为了获取更加真实、酷炫的3d物体，就需要用到纹理映射，就相当在前面我们画的形状前再贴一张皮肤上去，下面来看下怎么实现。

首先我们需要知道一张纹理图也是有自己的坐标， 二维纹理的坐标分为 S 轴和 T 轴，左上角坐标为（0， 0）点，最大为1个单位长度，所以长度的取值范围为 0 到 1，在这里说明下，不管纹理的长宽大小如何，它的取值范围都是从 0 到 1，所以假如纹理的高为255，宽为128，他们的取值范围都是 0 到 1； 
还有个问题需要注意的是opengl规定纹理图片尺寸的宽和高必须为2的n次方， 如 32 * 256等；

(0, 0) -----------------s----------------> (1, 0)
	|    |----------------------------|
	|    |                            |
	|    |                            |
	|    |                            |
	|    |                            |
	|    |       Texture              |
	t    |                            |
	|    |                            |
	|    |                            |
	|    |                            |
	|    |                            |
	|    |----------------------------|
	V (0, 1)
	

这里写图片描述

基本原理是： 
1.首先图元中的每个顶点都需要在顶点着色器中通过易变变量将纹理坐标传入片元着色器。 
2.经过顶点着色器后渲染管线的固定功能部分会根据情况进行插值计算，产生对应到每个片元的用于记录纹理坐标的易变变量值。 
3.最后每个片元在片元着色器中根据其接收到的记录纹理坐标的易变变量值到纹理图中提取出对应位置的颜色即可，提取颜色的过程一般称为纹理采样。

下面来看下程序怎么实现的：

public class MyGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context mContext;
    private Square mSquare;

    private float[] mMVPMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mCameraMatrix = new float[16];

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0.5f, 0);
        mSquare = new Square(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / (float) height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, 5f, 0, 0, 0, 0, 1f, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mCameraMatrix, 0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mSquare.draw(mMVPMatrix);
    }
}

public class Square {
    Context mContext;

    // 正方形的顶点坐标
    private float[] SQUARE_COORDS = {
            -0.5f, -0.5f,
            0.5f, -0.5f,
            -0.5f, 0.5f,
            0.5f, 0.5f
    };

    // 纹理的顶点坐标
    private float[] TEXTURE_COORDS = {
            0, 1,
            1, 1,
            0, 0,
            1, 0
    };

    private static final int COORDS_PER_VERTEX = 2;

    private FloatBuffer mSquareCoordsBuffer;
    private FloatBuffer mTextureCoordsBuffer;

    private static final String VERTEX_SHADER =
            "attribute vec4 aPosition;" +
            "uniform mat4 uMVPMatrix;" +
            "attribute vec2 aTextureCoords;" +
            "varying vec2 vTextureCoords;" +
            "void main() {" +
            "    gl_Position = uMVPMatrix * aPosition;" +
            "    vTextureCoords = aTextureCoords;" +
            "}";

    private static final String FRAGMENT_SHADER =
            "precision mediump float;" +
            "uniform sampler2D uTextureUnit;" + // sampler2D 表示用于访问2D纹理的采样器
            "varying vec2 vTextureCoords;" +
            "void main() {" +
            "    gl_FragColor = texture2D(uTextureUnit, vTextureCoords);" + // 进行纹理采样
            "}";

    private static final String POSITION_NAME = "aPosition";
    private static final String COLOR_NAME = "vColor";
    private static final String MVP_MATRIX_NAME = "uMVPMatrix";
    private static final String TEXTURE_VERTEXT_NAME = "aTextureCoords";

    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandle;
    private int mTextureVertextHandle;

    private float[] mTransformMatrix = new float[16];

    public Square(Context context) {
        mContext = context;

        mSquareCoordsBuffer = ByteBuffer.allocateDirect(SQUARE_COORDS.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mSquareCoordsBuffer.put(SQUARE_COORDS).position(0);

        mTextureCoordsBuffer = ByteBuffer.allocateDirect(TEXTURE_COORDS.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoordsBuffer.put(TEXTURE_COORDS).position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,VERTEX_SHADER);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        GLES20.glUseProgram(program);

        mPositionHandle = GLES20.glGetAttribLocation(program, POSITION_NAME);
        mColorHandle = GLES20.glGetUniformLocation(program, COLOR_NAME);
        mMatrixHandle = GLES20.glGetUniformLocation(program, MVP_MATRIX_NAME);
        mTextureVertextHandle = GLES20.glGetAttribLocation(program, TEXTURE_VERTEXT_NAME);

        TextureHelper.loadTexture(context.getResources(), R.drawable.texture);

        Matrix.setIdentityM(mTransformMatrix, 0);
        Matrix.rotateM(mTransformMatrix, 0, 90, 0, 0, 1.0f);
    }

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, 0, mSquareCoordsBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, mTransformMatrix, 0);

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glVertexAttribPointer(mTextureVertextHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, 0, mTextureCoordsBuffer);
        GLES20.glEnableVertexAttribArray(mTextureVertextHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureVertextHandle);
    }

}

public class TextureHelper {

    public static int loadTexture(Resources res, int resId) {
        final int[] textureId = new int[1];
        // 生成文理ID
        GLES20.glGenTextures(1, textureId, 0);
        // 绑定文理ID
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        // 设置缩小时采用最近点采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        // 设置放大时采用线性采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        final Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        // 加载纹理进显存
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        return textureId[0];
    }

}

其中 uniform sampler2D uTextureUnit; 中的 uTextureUnit 代表的是 
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]); 指定编号纹理的内容。

FRAGMENT_SHADER 的主要功能为根据接收的记录片元纹理坐标的易变变量中的纹理坐标，调用 
texture2D 内建函数从采样器中进行纹理采样，得到此片元的颜色值。最后，将采样到的颜色值传给 gl_FragColor 内建变量，完成片元的着色。

下面来说说纹理采样： 
GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, 
GLES20.GL_NEAREST); 
GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, 
GLES20.GL_LINEAR);

最近点采样（NEAREST）： 
原理：根据片元的纹理坐标可以计算出片元对应的纹理坐标点位于纹理图中的哪个像素中，最近点采样就直接取此像素的颜色值为采样值。 
优点：采样简单，计算速度快，在缩小情况下一般锯齿也不明显，综合效益高； 
缺点：在放大方式下锯齿会很明显，严重影响视觉效果；

线性纹理采样（LINEAR）： 
原理：采样时对采样范围内的多个像素进行了加权平均计算出最终的采样结果； 
优点：放大时不会有锯齿的现象，而是平滑过渡； 
缺点：采样较复杂，计算速度较慢；

mipmap 纹理技术： 
原理：根据原始图像生成一组由大到小的纹理图像，每幅纹理图是前一幅的1/2，直到纹理图尺寸缩小到 1*1像素， 渲染管线会首先根据情况计算出细节级别，然后根据细节级别决定使用系列中哪一个分辨率的纹理图； 
优点：可以根据不同情况选择使用哪张纹理图，所以纹理显示最清晰； 
缺点：由于需要生成多张图片，占用的空间接近原始纹理图的两倍；