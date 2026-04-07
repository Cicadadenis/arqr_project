package com.example.arqr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.core.Anchor
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color

class ARActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    private var markerLat = 47.8388
    private var markerLng = 35.1396

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        arFragment.arSceneView.scene.addOnUpdateListener {
            val earth = arFragment.arSceneView.session?.earth
            if (earth?.trackingState == TrackingState.TRACKING) {
                val anchor = earth.createAnchor(
                    markerLat,
                    markerLng,
                    0.0,
                    0.0f, 0.0f, 0.0f, 1.0f
                )
                placeMarker(anchor)
            }
        }
    }

    private fun placeMarker(anchor: Anchor) {
        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
            .thenAccept { material ->
                val sphere = ShapeFactory.makeSphere(0.2f, Vector3.zero(), material)
                val node = AnchorNode(anchor)
                node.renderable = sphere
                arFragment.arSceneView.scene.addChild(node)
            }
    }
}
