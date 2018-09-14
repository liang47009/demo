Ϊ�˻�ȡ������ʵ�����ŵ�3d���壬����Ҫ�õ�����ӳ�䣬���൱��ǰ�����ǻ�����״ǰ����һ��Ƥ����ȥ��������������ôʵ�֡�

����������Ҫ֪��һ������ͼҲ�����Լ������꣬ ��ά����������Ϊ S ��� T �ᣬ���Ͻ�����Ϊ��0�� 0���㣬���Ϊ1����λ���ȣ����Գ��ȵ�ȡֵ��ΧΪ 0 �� 1��������˵���£���������ĳ����С��Σ�����ȡֵ��Χ���Ǵ� 0 �� 1�����Լ�������ĸ�Ϊ255����Ϊ128�����ǵ�ȡֵ��Χ���� 0 �� 1�� 
���и�������Ҫע�����opengl�涨����ͼƬ�ߴ�Ŀ�͸߱���Ϊ2��n�η��� �� 32 * 256�ȣ�

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
	

����дͼƬ����

����ԭ���ǣ� 
1.����ͼԪ�е�ÿ�����㶼��Ҫ�ڶ�����ɫ����ͨ���ױ�������������괫��ƬԪ��ɫ���� 
2.����������ɫ������Ⱦ���ߵĹ̶����ܲ��ֻ����������в�ֵ���㣬������Ӧ��ÿ��ƬԪ�����ڼ�¼����������ױ����ֵ�� 
3.���ÿ��ƬԪ��ƬԪ��ɫ���и�������յ��ļ�¼����������ױ����ֵ������ͼ����ȡ����Ӧλ�õ���ɫ���ɣ���ȡ��ɫ�Ĺ���һ���Ϊ���������

���������³�����ôʵ�ֵģ�

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

    // �����εĶ�������
    private float[] SQUARE_COORDS = {
            -0.5f, -0.5f,
            0.5f, -0.5f,
            -0.5f, 0.5f,
            0.5f, 0.5f
    };

    // ����Ķ�������
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
            "uniform sampler2D uTextureUnit;" + // sampler2D ��ʾ���ڷ���2D����Ĳ�����
            "varying vec2 vTextureCoords;" +
            "void main() {" +
            "    gl_FragColor = texture2D(uTextureUnit, vTextureCoords);" + // �����������
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
        // ��������ID
        GLES20.glGenTextures(1, textureId, 0);
        // ������ID
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        // ������Сʱ������������
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        // ���÷Ŵ�ʱ�������Բ���
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        final Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        // ����������Դ�
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        return textureId[0];
    }

}

���� uniform sampler2D uTextureUnit; �е� uTextureUnit ������� 
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]); ָ�������������ݡ�

FRAGMENT_SHADER ����Ҫ����Ϊ���ݽ��յļ�¼ƬԪ����������ױ�����е��������꣬���� 
texture2D �ڽ������Ӳ������н�������������õ���ƬԪ����ɫֵ����󣬽�����������ɫֵ���� gl_FragColor �ڽ����������ƬԪ����ɫ��

������˵˵��������� 
GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, 
GLES20.GL_NEAREST); 
GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, 
GLES20.GL_LINEAR);

����������NEAREST���� 
ԭ������ƬԪ������������Լ����ƬԪ��Ӧ�����������λ������ͼ�е��ĸ������У�����������ֱ��ȡ�����ص���ɫֵΪ����ֵ�� 
�ŵ㣺�����򵥣������ٶȿ죬����С�����һ����Ҳ�����ԣ��ۺ�Ч��ߣ� 
ȱ�㣺�ڷŴ�ʽ�¾�ݻ�����ԣ�����Ӱ���Ӿ�Ч����

�������������LINEAR���� 
ԭ������ʱ�Բ�����Χ�ڵĶ�����ؽ����˼�Ȩƽ����������յĲ�������� 
�ŵ㣺�Ŵ�ʱ�����о�ݵ����󣬶���ƽ�����ɣ� 
ȱ�㣺�����ϸ��ӣ������ٶȽ�����

mipmap �������� 
ԭ������ԭʼͼ������һ���ɴ�С������ͼ��ÿ������ͼ��ǰһ����1/2��ֱ������ͼ�ߴ���С�� 1*1���أ� ��Ⱦ���߻����ȸ�����������ϸ�ڼ���Ȼ�����ϸ�ڼ������ʹ��ϵ������һ���ֱ��ʵ�����ͼ�� 
�ŵ㣺���Ը��ݲ�ͬ���ѡ��ʹ����������ͼ������������ʾ�������� 
ȱ�㣺������Ҫ���ɶ���ͼƬ��ռ�õĿռ�ӽ�ԭʼ����ͼ��������