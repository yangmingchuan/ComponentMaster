package com.ymc.lib_ijk.ijk.render.effect;

import android.opengl.GLSurfaceView;

import com.ymc.lib_ijk.ijk.render.view.IJKVideoGLView;


/**
 * 黑白滤镜，
 * Converts the video into black and white colors
 *
 * @author sheraz.khilji
 */
public class BlackAndWhiteEffect implements IJKVideoGLView.ShaderInterface {
    /**
     * Initialize Effect
     */
    public BlackAndWhiteEffect() {
    }

    @Override
    public String getShader(GLSurfaceView mGlSurfaceView) {

        String shader = "#extension GL_OES_EGL_image_external : require\n"
                + "precision mediump float;\n"
                + "varying vec2 vTextureCoord;\n"
                + "uniform samplerExternalOES sTexture;\n" + "void main() {\n"
                + "  vec4 color = texture2D(sTexture, vTextureCoord);\n"
                + "  float colorR = (color.r + color.g + color.b) / 3.0;\n"
                + "  float colorG = (color.r + color.g + color.b) / 3.0;\n"
                + "  float colorB = (color.r + color.g + color.b) / 3.0;\n"
                + "  gl_FragColor = vec4(colorR, colorG, colorB, color.a);\n"
                + "}\n";

        return shader;

    }
}
