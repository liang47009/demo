precision mediump float;
uniform sampler2D uTextureUnit;// sampler2D 表示用于访问2D纹理的采样器
varying vec2 vTextureCoords;
void main() {
    gl_FragColor = texture2D(uTextureUnit, vTextureCoords); // 进行纹理采样
}