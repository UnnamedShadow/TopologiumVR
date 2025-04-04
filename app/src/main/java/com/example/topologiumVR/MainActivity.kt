package com.example.topologiumVR

import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.meta.spatial.core.Entity
import com.meta.spatial.core.Pose
import com.meta.spatial.core.Quaternion
import com.meta.spatial.core.SpatialFeature
import com.meta.spatial.core.Vector3
import com.meta.spatial.runtime.ButtonBits
import com.meta.spatial.runtime.LayerConfig
import com.meta.spatial.runtime.ReferenceSpace
import com.meta.spatial.toolkit.AppSystemActivity
import com.meta.spatial.toolkit.Grabbable
import com.meta.spatial.toolkit.GrabbableSystem
import com.meta.spatial.toolkit.Panel
import com.meta.spatial.toolkit.PanelRegistration
import com.meta.spatial.toolkit.Transform
import com.meta.spatial.vr.VRFeature

class MainActivity : AppSystemActivity() {
    override fun registerFeatures(): List<SpatialFeature> {
        val features = mutableListOf<SpatialFeature>(VRFeature(this))
        return features
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        systemManager.findSystem<GrabbableSystem>().grabButtons =
            (ButtonBits.ButtonSqueezeR or ButtonBits.ButtonSqueezeL or ButtonBits.ButtonX)
    }

    override fun onSceneReady() {
        super.onSceneReady()

        scene.enablePassthrough(true)
        scene.setReferenceSpace(ReferenceSpace.LOCAL_FLOOR)
        scene.setViewOrigin(0f, 0f, 0f, 0f)

    }

    override fun registerPanels(): List<PanelRegistration> {
        return mutableListOf(
            PanelRegistration(R.integer.equation_panel_id) {
                activityClass = EquationPanel::class.java
                config {
                    fractionOfScreen = 0.75f
                    height = 1f
                    width = 2f
                    layoutDpi = 100
                    includeGlass = true
                    layerConfig = LayerConfig()
                    enableTransparent = false
                    // enable handgrab
                    clickButtons =
                        (ButtonBits.ButtonA or ButtonBits.ButtonTriggerL or ButtonBits.ButtonTriggerR)
                }
            }
        )
    }

    private fun requestPermissions() {
        val permissionsNeeded =
            arrayOf(
                "com.oculus.permission.USE_SCENE",
                )

        ActivityCompat.requestPermissions(this, permissionsNeeded, 100)
    }

    private var isFirstReadyDone: Boolean = false

    override fun onVRReady() {
        super.onVRReady()
        if (!isFirstReadyDone) {
            Entity(R.integer.equation_panel_id)
                .setComponents(listOf(
                    Grabbable(),
                    Panel(R.integer.equation_panel_id),
                    Transform(
                        Pose() * Pose(Vector3(-1f, 1.25f, 1.2f), Quaternion(0f, 0f, 0f))),
                ))
            isFirstReadyDone = true
        }
    }
}
