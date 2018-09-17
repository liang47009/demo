attribute vec4 aPosition;
uniform mat4 uMVPMatrix;

attribute vec4 aColor;
varying vec4 vColor;

void main() {
   gl_Position = uMVPMatrix * aPosition;
   vColor = aColor;
}
