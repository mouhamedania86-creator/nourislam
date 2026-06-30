package com.example

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlin.math.atan2
import kotlin.math.roundToInt

/**
 * مدير البوصلة المحسّن
 * يستخدم TYPE_ROTATION_VECTOR (الحديث) + تسهيل السنسور
 * يعطي قيمة headingNorth بالدرجات (0-360)
 */
class QiblaCompassManager(
    private val context: Context,
    private val onHeadingChanged: (Float) -> Unit
) : SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Try modern sensor first, fallback to deprecated
    private val rotationVectorSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let {
                // Last resort: use accelerometer + magnetometer combination
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            }?.let { null }

    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magnetometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val rotationMatrix = FloatArray(9)
    private val orientationValues = FloatArray(3)
    private val rotationVector = FloatArray(5)

    // Fallback for accelerometer + magnetometer
    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)
    private var hasGravity = false
    private var hasGeomagnetic = false

    private var lastEmittedHeading = -1f

    fun start() {
        // Modern sensor
        if (rotationVectorSensor != null) {
            sensorManager.registerListener(
                this,
                rotationVectorSensor,
                SensorManager.SENSOR_DELAY_UI
            )
            Log.d(TAG, "✅ Using TYPE_ROTATION_VECTOR")
            return
        }

        // Fallback: accelerometer + magnetometer
        if (accelerometer != null && magnetometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
            Log.d(TAG, "✅ Using ACCELEROMETER + MAGNETIC_FIELD")
            return
        }

        Log.e(TAG, "❌ No suitable sensors found")
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                try {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    SensorManager.getOrientation(rotationMatrix, orientationValues)
                    // orientationValues[0] = azimuth in radians (0 = North)
                    val azimuthDegrees = Math.toDegrees(orientationValues[0].toDouble())
                        .toFloat()
                    val normalized = (azimuthDegrees + 360f) % 360f
                    emitHeading(normalized)
                } catch (e: Exception) {
                    Log.e(TAG, "Rotation vector error", e)
                }
            }
            Sensor.TYPE_ORIENTATION -> {
                val azimuth = event.values[0]
                emitHeading(azimuth)
            }
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(event.values, 0, gravity, 0, 3)
                hasGravity = true
                computeFallback()
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, geomagnetic, 0, 3)
                hasGeomagnetic = true
                computeFallback()
            }
        }
    }

    private fun computeFallback() {
        if (hasGravity && hasGeomagnetic) {
            val success = SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)
            if (success) {
                SensorManager.getOrientation(rotationMatrix, orientationValues)
                val azimuthDegrees = Math.toDegrees(orientationValues[0].toDouble()).toFloat()
                val normalized = (azimuthDegrees + 360f) % 360f
                emitHeading(normalized)
            }
        }
    }

    private fun emitHeading(heading: Float) {
        // Throttle: only emit if changed by at least 0.5 degree
        if (kotlin.math.abs(heading - lastEmittedHeading) >= 0.5f) {
            lastEmittedHeading = heading
            onHeadingChanged(heading)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No-op
    }

    /**
     * حساب زاوية القبلة من موقع المستخدم
     * @return زاوية بالدرجات من الشمال (0-360)
     */
    fun calculateQiblaAngle(userLat: Double, userLng: Double): Float {
        val meccaLat = Math.toRadians(21.4225) // Kaaba latitude
        val meccaLng = Math.toRadians(39.8262) // Kaaba longitude
        val uLat = Math.toRadians(userLat)
        val uLng = Math.toRadians(userLng)

        val dLng = meccaLng - uLng
        val y = kotlin.math.sin(dLng) * kotlin.math.cos(meccaLat)
        val x = kotlin.math.cos(uLat) * kotlin.math.sin(meccaLat) -
                kotlin.math.sin(uLat) * kotlin.math.cos(meccaLat) * kotlin.math.cos(dLng)

        var bearing = Math.toDegrees(kotlin.math.atan2(y, x).toDouble()).toFloat()
        bearing = (bearing + 360f) % 360f
        return bearing
    }

    /**
     * هل التليفون موجّه للقبلة؟
     * @param heading الزاوية الحالية للتليفون من الشمال
     * @param qiblaAngle زاوية القبلة المحسوبة
     * @param tolerance هامش الخطأ (افتراضياً 5°)
     */
    fun isPointingToQibla(
        heading: Float,
        qiblaAngle: Float,
        tolerance: Float = 5f
    ): Boolean {
        val diff = kotlin.math.abs(((heading - qiblaAngle + 540f) % 360f) - 180f)
        return diff <= tolerance
    }

    companion object {
        private const val TAG = "QiblaCompassManager"
    }
}
