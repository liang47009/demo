attribute vec4 aPosition;
uniform mat4 uMVPMatrix;
attribute vec2 aTextureCoords;
varying vec2 vTextureCoords;
void main() {
    gl_Position = uMVPMatrix * aPosition;
    vTextureCoords = aTextureCoords;
}