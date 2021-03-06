#ifndef __PROGRAMID_H__
#define __PROGRAMID_H__

#include <GLES2/gl2.h>
#include <vector>
#include <string>
#include <map>

class ShaderId {
public:
    ShaderId() {
        _shaderId = -1;
    }

    GLint _shaderId;
};


/**
*   程序
*/
class ProgramId {
private:
    std::map<std::string, int> attributs;
public:
    GLint _programId;
    ShaderId _vertex;
    ShaderId _fragment;
public:
    ProgramId() {
        _programId = -1;
    }

public:
    virtual bool initialize() = 0;

    int getShaderHandler(const char *key) {
        return attributs[key];
    }

    void addShaderHandler(const char *key, int handler) {
        attributs[key] = handler;
    }

    /**
    *   加载函数
    */
    bool createProgram(const char *vertex, const char *fragment) {
        bool error = false;
        do {
            if (vertex) {
                _vertex._shaderId = glCreateShader(GL_VERTEX_SHADER);
                glShaderSource(_vertex._shaderId, 1, &vertex, 0);
                glCompileShader(_vertex._shaderId);

                GLint compileStatus;
                glGetShaderiv(_vertex._shaderId, GL_COMPILE_STATUS, &compileStatus);
                error = compileStatus == GL_FALSE;
                if (error) {
                    GLchar messages[256];
                    glGetShaderInfoLog(_vertex._shaderId, sizeof(messages), 0, messages);
                    break;
                }
            }
            if (fragment) {
                _fragment._shaderId = glCreateShader(GL_FRAGMENT_SHADER);
                glShaderSource(_fragment._shaderId, 1, &fragment, 0);
                glCompileShader(_fragment._shaderId);

                GLint compileStatus;
                glGetShaderiv(_fragment._shaderId, GL_COMPILE_STATUS, &compileStatus);
                error = compileStatus == GL_FALSE;

                if (error) {
                    GLchar messages[256];
                    glGetShaderInfoLog(_fragment._shaderId, sizeof(messages), 0, messages);
                    break;
                }
            }
            _programId = glCreateProgram();

            if (_vertex._shaderId) {
                glAttachShader(_programId, _vertex._shaderId);
            }
            if (_fragment._shaderId) {
                glAttachShader(_programId, _fragment._shaderId);
            }

            glLinkProgram(_programId);

            GLint linkStatus;
            glGetProgramiv(_programId, GL_LINK_STATUS, &linkStatus);
            if (linkStatus == GL_FALSE) {
                GLchar messages[256];
                glGetProgramInfoLog(_programId, sizeof(messages), 0, messages);
                break;
            }
            glUseProgram(_programId);

        } while (false);

        if (error) {
            if (_fragment._shaderId) {
                glDeleteShader(_fragment._shaderId);
                _fragment._shaderId = 0;
            }
            if (_vertex._shaderId) {
                glDeleteShader(_vertex._shaderId);
                _vertex._shaderId = 0;
            }
            if (_programId) {
                glDeleteProgram(_programId);
                _programId = 0;
            }
        }
        return true;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);

    }

    /**
    *   使用完成
    */
    virtual void end() {
        glUseProgram(0);
    }
};

class PROGRAM_Triangle : public ProgramId {
public:
    int _position;
    int _color;
public:
    PROGRAM_Triangle() {
        _position = -1;
        _color = -1;
    }

    ~PROGRAM_Triangle() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "attribute vec4 _position;"
                                "void main() {"
                                "   gl_Position = _position;"
                                "}"
                };
        const char *ps =
                {
                        "uniform vec4 _color;"
                                "void main() {"
                                "   gl_FragColor = _color;"
                                "}"
                };
        bool res = createProgram(vs, ps);
        if (res) {
            _position = glGetAttribLocation(_programId, "_position");
            _color = glGetUniformLocation(_programId, "_color");
        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_position);
    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_position);
        glUseProgram(0);
    }
};

class PROGRAM_TR_TEX : public ProgramId {
public:
    PROGRAM_TR_TEX() {
    }

    ~PROGRAM_TR_TEX() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "attribute vec4 vPosition;"
                                "attribute vec2 vCoordinate;"
                                "uniform mat4 vMatrix;"
                                "varying vec2 aCoordinate;"
                                "void main(){"
                                "    gl_Position=vMatrix*vPosition;"
                                "    aCoordinate=vCoordinate;"
                                "}"
                };
        const char *ps =
                {
                        "precision mediump float;"
                                "uniform sampler2D vTexture;"
                                "varying vec2 aCoordinate;"
                                "void main(){"
                                "    gl_FragColor=texture2D(vTexture,aCoordinate);"
                                "}"
                };
        bool res = createProgram(vs, ps);
        if (res) {
            int vPosition = glGetAttribLocation(_programId, "vPosition");
            int vTexCoord = glGetAttribLocation(_programId, "vCoordinate");
            int uMatrix = glGetUniformLocation(_programId, "vMatrix");
            int fTexture = glGetUniformLocation(_programId, "vTexture");

            addShaderHandler("vPosition", vPosition);
            addShaderHandler("vCoordinate", vTexCoord);
            addShaderHandler("vMatrix", uMatrix);
            addShaderHandler("vTexture", fTexture);
        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
    }

    /**
    *   使用完成
    */
    virtual void end() {
        glUseProgram(0);
    }
};

class PROGRAM_Tr_U1 : public ProgramId {
public:
    PROGRAM_Tr_U1() {
    }

    ~PROGRAM_Tr_U1() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "attribute vec4 _position;"
                                "uniform mat4 _mvp;"
                                "void main() {"
                                "   gl_Position = _mvp * _position;"
                                "}"
                };
        const char *ps =
                {
                        "uniform vec4 _color;"
                                "void main() {"
                                "   gl_FragColor = _color;"
                                "}"
                };
        bool res = createProgram(vs, ps);
        if (res) {
            int _position = glGetAttribLocation(_programId, "_position");
            int _color = glGetUniformLocation(_programId, "_color");
            int _mvp = glGetUniformLocation(_programId, "_mvp");

            addShaderHandler("_position", _position);
            addShaderHandler("_color", _color);
            addShaderHandler("_mvp", _mvp);

        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
    }

    /**
    *   使用完成
    */
    virtual void end() {
        glUseProgram(0);
    }
};

class PROGRAM_Tr_TEX : public ProgramId {
public:
    int _position;
    int _color;
    int _mvp;
public:
    PROGRAM_Tr_TEX() {
        _position = -1;
        _color = -1;
        _mvp = -1;
    }

    ~PROGRAM_Tr_TEX() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "attribute vec4 _position;"
                                "uniform mat4 _mvp;"
                                "void main() {"
                                "   gl_Position = _mvp * _position;"
                                "}"
                };
        const char *ps =
                {
                        "uniform vec4 _color;"
                                "void main() {"
                                "   gl_FragColor = _color;"
                                "}"
                };
        bool res = createProgram(vs, ps);
        if (res) {
            _position = glGetAttribLocation(_programId, "_position");
            _color = glGetUniformLocation(_programId, "_color");
            _mvp = glGetUniformLocation(_programId, "_mvp");
        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_position);
    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_position);
        glUseProgram(0);
    }
};

class PROGRAM_P2_C4 : public ProgramId {
public:
    typedef int attribute;
    typedef int uniform;
public:
    attribute _position;
    uniform _color;
    uniform _MVP;
public:
    PROGRAM_P2_C4() {
        _position = -1;
        _color = -1;
        _MVP = -1;
    }

    ~PROGRAM_P2_C4() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "precision lowp float; "
                                "uniform   mat4 _MVP;"
                                "attribute vec2 _position;"
                                "void main()"
                                "{"
                                "   vec4    pos =   vec4(_position,0,1);"
                                "   gl_Position =   _MVP * pos;"
                                "}"
                };
        const char *ps =
                {
                        "precision  lowp float; "
                                "uniform    vec4 _color;"
                                "void main()"
                                "{"
                                "   gl_FragColor   =   _color;"
                                "}"
                };

        bool res = createProgram(vs, ps);
        if (res) {
            _position = glGetAttribLocation(_programId, "_position");
            _color = glGetUniformLocation(_programId, "_color");
            _MVP = glGetUniformLocation(_programId, "_MVP");
        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_position);

    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_position);
        glUseProgram(0);
    }
};

class PROGRAM_P2_AC4 : public ProgramId {
public:
    typedef int attribute;
    typedef int uniform;
public:
    attribute _position;
    attribute _color;
    uniform _MVP;
public:
    PROGRAM_P2_AC4() {
        _position = -1;
        _color = -1;
        _MVP = -1;
    }

    ~PROGRAM_P2_AC4() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "precision lowp float; "
                                "uniform   mat4 _MVP;"
                                "attribute vec2 _position;"
                                "attribute vec4 _color;"
                                "varying   vec4 _outColor;"

                                "void main()"
                                "{"
                                "   vec4    pos =   vec4(_position,0,1);"
                                "   _outColor   =   _color;"
                                "   gl_Position =   _MVP * pos;"
                                "}"
                };
        const char *ps =
                {
                        "precision  lowp float; "
                                "varying   vec4 _outColor;"
                                "void main()"
                                "{"
                                "   gl_FragColor   =   _outColor;"
                                "}"
                };

        bool res = createProgram(vs, ps);
        if (res) {
            _position = glGetAttribLocation(_programId, "_position");
            _color = glGetAttribLocation(_programId, "_color");
            _MVP = glGetUniformLocation(_programId, "_MVP");
        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_position);
        glEnableVertexAttribArray(_color);

    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_position);
        glDisableVertexAttribArray(_color);
        glUseProgram(0);
    }
};

class PROGRAM_P2_UV_AC4 : public ProgramId {
public:
    typedef int attribute;
    typedef int uniform;
public:
    attribute _position;
    attribute _color;
    attribute _uv;
    uniform _MVP;
    uniform _texture;
    //!  第二阶段文理
    uniform _texture1;
    uniform _uvAnim;
public:
    PROGRAM_P2_UV_AC4() {
        _position = -1;
        _color = -1;
        _uv = -1;
        _texture = -1;
        _texture1 = -1;
        _uvAnim = -1;
        _MVP = -1;
    }

    ~PROGRAM_P2_UV_AC4() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "precision lowp float; "
                                "uniform   mat4 _MVP;"
                                "attribute vec2 _position;"
                                "attribute vec2 _uv;"
                                "attribute vec4 _color;"
                                "varying   vec4 _outColor;"
                                "varying   vec2 _outUV;"

                                "void main()"
                                "{"
                                "   vec4    pos =   vec4(_position,0,1);"
                                "   _outColor   =   _color;"
                                "   _outUV      =   _uv;"
                                "   gl_Position =   _MVP * pos;"
                                "}"
                };
        const char *ps =
                {
                        "precision  lowp float; "
                                "uniform   sampler2D _texture;\n"
                                "uniform   sampler2D _texture1;\n"
                                "uniform   float    _uvAnim;\n"
                                "varying   vec4      _outColor;\n"
                                "varying   vec2      _outUV;\n"
                                "void main()"
                                "{"
                                "   vec4    tColor0  =   texture2D(_texture,_outUV);\n"
                                "   vec2    newUV = vec2(_uvAnim + _outUV.x,_outUV.y);"
                                "   vec4    tColor1  =   texture2D(_texture1,newUV);\n"
                                "   gl_FragColor    =   tColor0  * tColor1;\n"
                                "}"
                };

        bool res = createProgram(vs, ps);
        if (res) {
            _position = glGetAttribLocation(_programId, "_position");
            _color = glGetAttribLocation(_programId, "_color");
            _uv = glGetAttribLocation(_programId, "_uv");
            _texture = glGetUniformLocation(_programId, "_texture");
            _texture1 = glGetUniformLocation(_programId, "_texture1");
            _MVP = glGetUniformLocation(_programId, "_MVP");
            _uvAnim = glGetUniformLocation(_programId, "_uvAnim");


        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_position);
        glEnableVertexAttribArray(_uv);
        glEnableVertexAttribArray(_color);

    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_position);
        glDisableVertexAttribArray(_uv);
        glDisableVertexAttribArray(_color);
        glUseProgram(0);
    }
};

class PROGRAM_P3_TEX : public ProgramId {
public:
    typedef int attribute;
    typedef int uniform;
public:
    attribute _position;
    attribute _uv;
    uniform _MVP;
    uniform _texture;
public:
    PROGRAM_P3_TEX() {
        _position = -1;
        _uv = -1;
        _texture = -1;
        _MVP = -1;
    }

    ~PROGRAM_P3_TEX() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "precision mediump float; "
                                "uniform   mat4 _MVP;"
                                "attribute vec4 _position;"
                                "attribute vec2 _uv;"
                                "varying   vec2 _outUV;"
                                "void main()"
                                "{"
                                "   gl_Position =   _MVP * _position;"
                                "   _outUV      =   _uv;"
                                "}"
                };
        const char *ps =
                {
                        "precision  mediump float; "
                                "uniform   sampler2D _texture;"
                                "varying   vec2      _outUV;"
                                "void main()"
                                "{"
                                "   gl_FragColor = texture2D(_texture,_outUV);"
                                "}"
                };

        bool res = createProgram(vs, ps);
        if (res) {
            _position = glGetAttribLocation(_programId, "_position");
            _uv = glGetAttribLocation(_programId, "_uv");
            _texture = glGetUniformLocation(_programId, "_texture");
            _MVP = glGetUniformLocation(_programId, "_MVP");

        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_uv);
        glEnableVertexAttribArray(_position);
    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_uv);
        glDisableVertexAttribArray(_position);
        glUseProgram(0);
    }
};

class PROGRAM_P3_UV : public ProgramId {
public:
    typedef int attribute;
    typedef int uniform;
public:
    attribute _position;
    attribute _uv;
    uniform _MVP;
    uniform _texture;
public:
    PROGRAM_P3_UV() {
        _position = -1;
        _uv = -1;
        _texture = -1;
        _MVP = -1;
    }

    ~PROGRAM_P3_UV() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "precision lowp float; "
                                "uniform   mat4 _MVP;"
                                "attribute vec2 _position;"
                                "attribute vec2 _uv;"
                                "varying   vec2 _outUV;"

                                "void main()"
                                "{"
                                "   vec4    pos =   vec4(_position,0,1);"
                                "   _outUV      =   _uv;"
                                "   gl_Position =   _MVP * pos;"
                                "}"
                };
        const char *ps =
                {
                        "precision  lowp float; "
                                "uniform   sampler2D _texture;\n"
                                "varying   vec2      _outUV;\n"
                                "void main()"
                                "{"
                                "   vec4    tColor0  =   texture2D(_texture,_outUV);\n"
                                "   gl_FragColor    =   tColor0;\n"
                                "}"
                };

        bool res = createProgram(vs, ps);
        if (res) {
            _position = glGetAttribLocation(_programId, "_position");
            _uv = glGetAttribLocation(_programId, "_uv");
            _texture = glGetUniformLocation(_programId, "_texture");
            _MVP = glGetUniformLocation(_programId, "_MVP");

        }
        return res;
    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_position);
        glEnableVertexAttribArray(_uv);

    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_position);
        glDisableVertexAttribArray(_uv);
        glUseProgram(0);
    }
};

class PROGRAM_P3_T2_C3 : public ProgramId {
public:
    typedef GLint location;
public:
    location _positionAttr;
    location _colorAttr;
    location _uvAttr;
    location _MVP;
    location _texture;
public:
    PROGRAM_P3_T2_C3() {
        _positionAttr = -1;
        _colorAttr = -1;
        _uvAttr = -1;
        _MVP = -1;
    }

    ~PROGRAM_P3_T2_C3() {
    }

    /// 初始化函数
    virtual bool initialize() {
        const char *vs =
                {
                        "precision lowp float;"
                                "uniform mat4 _MVP;"
                                "attribute vec3 _position;"
                                "attribute vec4 _color;"
                                "attribute vec2 _uv;"

                                "varying vec4 _outColor;"
                                "varying vec2 _outUV;"
                                "void main()"
                                "{"
                                "vec4 pos=vec4(_position.x,_position.y,_position.z,1);"
                                "gl_Position=_MVP*pos;"
                                "_outColor=_color;"
                                "_outUV=_uv;"
                                "}"
                };

        const char *ps =
                {
                        "precision lowp float;"
                                "uniform sampler2D _texture;"
                                "varying vec4 _outColor;"
                                "varying vec2 _outUV;"
                                "void main()"
                                "{"
                                "vec4 color=texture2D(_texture,_outUV);"//纹理采样
                                "gl_FragColor=color*_outColor;"//相乘变暗，相加变亮
                                "}"

                };

        bool res = createProgram(vs, ps);
        if (res) {
            //获取shader中变量引用
            _positionAttr = glGetAttribLocation(_programId, "_position");
            _colorAttr = glGetAttribLocation(_programId, "_color");
            _uvAttr = glGetAttribLocation(_programId, "_uv");
            _MVP = glGetUniformLocation(_programId, "_MVP");
            _texture = glGetUniformLocation(_programId, "_texture");
        }
        return res;


    }

    /**
    *   使用程序
    */
    virtual void begin() {
        glUseProgram(_programId);
        glEnableVertexAttribArray(_positionAttr);
        glEnableVertexAttribArray(_uvAttr);
        glEnableVertexAttribArray(_colorAttr);

    }

    /**
    *   使用完成
    */
    virtual void end() {
        glDisableVertexAttribArray(_positionAttr);
        glDisableVertexAttribArray(_uvAttr);
        glDisableVertexAttribArray(_colorAttr);
        glUseProgram(0);
    }
};

struct VertexTriangle {
    float x, y, z;
//        float u, v;
//        float r, g, b, a;
};

struct VertexSquare {
    float x;
    float y;

    VertexSquare(float x_, float y_) {
        x = x_;
        y = y_;
    }

};


#endif