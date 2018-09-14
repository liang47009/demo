/*
 * Copy_right 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * y_ou may_ not use this file ex_cept in compliance with the License.
 * You may_ obtain a copy_ of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by_ applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either ex_press or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//--------------------------------------------------------------------------------
// vecmath.cpp
//--------------------------------------------------------------------------------
#include "vecmath.h"

//--------------------------------------------------------------------------------
// vec3
//--------------------------------------------------------------------------------
Vec3::Vec3(const Vec4 &vec) {
    x_ = vec.x_;
    y_ = vec.y_;
    z_ = vec.z_;
}

//--------------------------------------------------------------------------------
// vec4
//--------------------------------------------------------------------------------
Vec4 Vec4::operator*(const Mat4 &rhs) const {
    Vec4 out;
    out.x_ = x_ * rhs.f_[0] + y_ * rhs.f_[1] + z_ * rhs.f_[2] + w_ * rhs.f_[3];
    out.y_ = x_ * rhs.f_[4] + y_ * rhs.f_[5] + z_ * rhs.f_[6] + w_ * rhs.f_[7];
    out.z_ = x_ * rhs.f_[8] + y_ * rhs.f_[9] + z_ * rhs.f_[10] + w_ * rhs.f_[11];
    out.w_ = x_ * rhs.f_[12] + y_ * rhs.f_[13] + z_ * rhs.f_[14] + w_ * rhs.f_[15];
    return out;
}

//--------------------------------------------------------------------------------
// mat4
//--------------------------------------------------------------------------------
Mat4::Mat4() {
    for (int32_t i = 0; i < 16; ++i)
        f_[i] = 0.f;
}

Mat4::Mat4(const float *mIn) {
    for (int32_t i = 0; i < 16; ++i)
        f_[i] = mIn[i];
}

Mat4 Mat4::operator*(const Mat4 &rhs) const {
    Mat4 ret;
    ret.f_[0] = f_[0] * rhs.f_[0] + f_[4] * rhs.f_[1] + f_[8] * rhs.f_[2]
                + f_[12] * rhs.f_[3];
    ret.f_[1] = f_[1] * rhs.f_[0] + f_[5] * rhs.f_[1] + f_[9] * rhs.f_[2]
                + f_[13] * rhs.f_[3];
    ret.f_[2] = f_[2] * rhs.f_[0] + f_[6] * rhs.f_[1] + f_[10] * rhs.f_[2]
                + f_[14] * rhs.f_[3];
    ret.f_[3] = f_[3] * rhs.f_[0] + f_[7] * rhs.f_[1] + f_[11] * rhs.f_[2]
                + f_[15] * rhs.f_[3];

    ret.f_[4] = f_[0] * rhs.f_[4] + f_[4] * rhs.f_[5] + f_[8] * rhs.f_[6]
                + f_[12] * rhs.f_[7];
    ret.f_[5] = f_[1] * rhs.f_[4] + f_[5] * rhs.f_[5] + f_[9] * rhs.f_[6]
                + f_[13] * rhs.f_[7];
    ret.f_[6] = f_[2] * rhs.f_[4] + f_[6] * rhs.f_[5] + f_[10] * rhs.f_[6]
                + f_[14] * rhs.f_[7];
    ret.f_[7] = f_[3] * rhs.f_[4] + f_[7] * rhs.f_[5] + f_[11] * rhs.f_[6]
                + f_[15] * rhs.f_[7];

    ret.f_[8] = f_[0] * rhs.f_[8] + f_[4] * rhs.f_[9] + f_[8] * rhs.f_[10]
                + f_[12] * rhs.f_[11];
    ret.f_[9] = f_[1] * rhs.f_[8] + f_[5] * rhs.f_[9] + f_[9] * rhs.f_[10]
                + f_[13] * rhs.f_[11];
    ret.f_[10] = f_[2] * rhs.f_[8] + f_[6] * rhs.f_[9] + f_[10] * rhs.f_[10]
                 + f_[14] * rhs.f_[11];
    ret.f_[11] = f_[3] * rhs.f_[8] + f_[7] * rhs.f_[9] + f_[11] * rhs.f_[10]
                 + f_[15] * rhs.f_[11];

    ret.f_[12] = f_[0] * rhs.f_[12] + f_[4] * rhs.f_[13] + f_[8] * rhs.f_[14]
                 + f_[12] * rhs.f_[15];
    ret.f_[13] = f_[1] * rhs.f_[12] + f_[5] * rhs.f_[13] + f_[9] * rhs.f_[14]
                 + f_[13] * rhs.f_[15];
    ret.f_[14] = f_[2] * rhs.f_[12] + f_[6] * rhs.f_[13] + f_[10] * rhs.f_[14]
                 + f_[14] * rhs.f_[15];
    ret.f_[15] = f_[3] * rhs.f_[12] + f_[7] * rhs.f_[13] + f_[11] * rhs.f_[14]
                 + f_[15] * rhs.f_[15];

    return ret;
}

Vec4 Mat4::operator*(const Vec4 &rhs) const {
    Vec4 ret;
    ret.x_ = rhs.x_ * f_[0] + rhs.y_ * f_[4] + rhs.z_ * f_[8] + rhs.w_ * f_[12];
    ret.y_ = rhs.x_ * f_[1] + rhs.y_ * f_[5] + rhs.z_ * f_[9] + rhs.w_ * f_[13];
    ret.z_ = rhs.x_ * f_[2] + rhs.y_ * f_[6] + rhs.z_ * f_[10] + rhs.w_ * f_[14];
    ret.w_ = rhs.x_ * f_[3] + rhs.y_ * f_[7] + rhs.z_ * f_[11] + rhs.w_ * f_[15];
    return ret;
}

void Mat4::frustumM(Mat4 &ret, int offset,
                    float left, float right, float bottom, float top,
                    float near, float far) {
//    if (left == right) {
//        throw new IllegalArgumentException("left == right");
//    }
//    if (top == bottom) {
//        throw new IllegalArgumentException("top == bottom");
//    }
//    if (near == far) {
//        throw new IllegalArgumentException("near == far");
//    }
//    if (near <= 0.0f) {
//        throw new IllegalArgumentException("near <= 0.0f");
//    }
//    if (far <= 0.0f) {
//        throw new IllegalArgumentException("far <= 0.0f");
//    }
    float r_width = 1.0f / (right - left);
    float r_height = 1.0f / (top - bottom);
    float r_depth = 1.0f / (near - far);
    float x = 2.0f * (near * r_width);
    float y = 2.0f * (near * r_height);
    float A = (right + left) * r_width;
    float B = (top + bottom) * r_height;
    float C = (far + near) * r_depth;
    float D = 2.0f * (far * near * r_depth);

    ret.f_[offset + 0] = x;
    ret.f_[offset + 5] = y;
    ret.f_[offset + 8] = A;
    ret.f_[offset + 9] = B;
    ret.f_[offset + 10] = C;
    ret.f_[offset + 14] = D;
    ret.f_[offset + 11] = -1.0f;
    ret.f_[offset + 1] = 0.0f;
    ret.f_[offset + 2] = 0.0f;
    ret.f_[offset + 3] = 0.0f;
    ret.f_[offset + 4] = 0.0f;
    ret.f_[offset + 6] = 0.0f;
    ret.f_[offset + 7] = 0.0f;
    ret.f_[offset + 12] = 0.0f;
    ret.f_[offset + 13] = 0.0f;
    ret.f_[offset + 15] = 0.0f;
}

Mat4 Mat4::Inverse() {
    Mat4 ret;
    float det_1;
    float pos = 0;
    float neg = 0;
    float temp;

    temp = f_[0] * f_[5] * f_[10];
    if (temp >= 0)
        pos += temp;
    else
        neg += temp;
    temp = f_[4] * f_[9] * f_[2];
    if (temp >= 0)
        pos += temp;
    else
        neg += temp;
    temp = f_[8] * f_[1] * f_[6];
    if (temp >= 0)
        pos += temp;
    else
        neg += temp;
    temp = -f_[8] * f_[5] * f_[2];
    if (temp >= 0)
        pos += temp;
    else
        neg += temp;
    temp = -f_[4] * f_[1] * f_[10];
    if (temp >= 0)
        pos += temp;
    else
        neg += temp;
    temp = -f_[0] * f_[9] * f_[6];
    if (temp >= 0)
        pos += temp;
    else
        neg += temp;
    det_1 = pos + neg;

    if (det_1 == 0.0) {
        //Error
    } else {
        det_1 = 1.0f / det_1;
        ret.f_[0] = (f_[5] * f_[10] - f_[9] * f_[6]) * det_1;
        ret.f_[1] = -(f_[1] * f_[10] - f_[9] * f_[2]) * det_1;
        ret.f_[2] = (f_[1] * f_[6] - f_[5] * f_[2]) * det_1;
        ret.f_[4] = -(f_[4] * f_[10] - f_[8] * f_[6]) * det_1;
        ret.f_[5] = (f_[0] * f_[10] - f_[8] * f_[2]) * det_1;
        ret.f_[6] = -(f_[0] * f_[6] - f_[4] * f_[2]) * det_1;
        ret.f_[8] = (f_[4] * f_[9] - f_[8] * f_[5]) * det_1;
        ret.f_[9] = -(f_[0] * f_[9] - f_[8] * f_[1]) * det_1;
        ret.f_[10] = (f_[0] * f_[5] - f_[4] * f_[1]) * det_1;

        /* Calculate -C * inverse(A) */
        ret.f_[12] = -(f_[12] * ret.f_[0] + f_[13] * ret.f_[4] + f_[14] * ret.f_[8]);
        ret.f_[13] = -(f_[12] * ret.f_[1] + f_[13] * ret.f_[5] + f_[14] * ret.f_[9]);
        ret.f_[14] = -(f_[12] * ret.f_[2] + f_[13] * ret.f_[6] + f_[14] * ret.f_[10]);

        ret.f_[3] = 0.0f;
        ret.f_[7] = 0.0f;
        ret.f_[11] = 0.0f;
        ret.f_[15] = 1.0f;
    }

    *this = ret;
    return *this;
}

//--------------------------------------------------------------------------------
// Misc
//--------------------------------------------------------------------------------
Mat4 Mat4::RotationX(const float fAngle) {
    Mat4 ret;
    float fCosine, fSine;

    fCosine = cosf(fAngle);
    fSine = sinf(fAngle);

    ret.f_[0] = 1.0f;
    ret.f_[4] = 0.0f;
    ret.f_[8] = 0.0f;
    ret.f_[12] = 0.0f;
    ret.f_[1] = 0.0f;
    ret.f_[5] = fCosine;
    ret.f_[9] = fSine;
    ret.f_[13] = 0.0f;
    ret.f_[2] = 0.0f;
    ret.f_[6] = -fSine;
    ret.f_[10] = fCosine;
    ret.f_[14] = 0.0f;
    ret.f_[3] = 0.0f;
    ret.f_[7] = 0.0f;
    ret.f_[11] = 0.0f;
    ret.f_[15] = 1.0f;
    return ret;
}

Mat4 Mat4::RotationY(const float fAngle) {
    Mat4 ret;
    float fCosine, fSine;

    fCosine = cosf(fAngle);
    fSine = sinf(fAngle);

    ret.f_[0] = fCosine;
    ret.f_[4] = 0.0f;
    ret.f_[8] = -fSine;
    ret.f_[12] = 0.0f;
    ret.f_[1] = 0.0f;
    ret.f_[5] = 1.0f;
    ret.f_[9] = 0.0f;
    ret.f_[13] = 0.0f;
    ret.f_[2] = fSine;
    ret.f_[6] = 0.0f;
    ret.f_[10] = fCosine;
    ret.f_[14] = 0.0f;
    ret.f_[3] = 0.0f;
    ret.f_[7] = 0.0f;
    ret.f_[11] = 0.0f;
    ret.f_[15] = 1.0f;
    return ret;

}

Mat4 Mat4::RotationZ(const float fAngle) {
    Mat4 ret;
    float fCosine, fSine;

    fCosine = cosf(fAngle);
    fSine = sinf(fAngle);

    ret.f_[0] = fCosine;
    ret.f_[4] = fSine;
    ret.f_[8] = 0.0f;
    ret.f_[12] = 0.0f;
    ret.f_[1] = -fSine;
    ret.f_[5] = fCosine;
    ret.f_[9] = 0.0f;
    ret.f_[13] = 0.0f;
    ret.f_[2] = 0.0f;
    ret.f_[6] = 0.0f;
    ret.f_[10] = 1.0f;
    ret.f_[14] = 0.0f;
    ret.f_[3] = 0.0f;
    ret.f_[7] = 0.0f;
    ret.f_[11] = 0.0f;
    ret.f_[15] = 1.0f;
    return ret;
}

Mat4 Mat4::Translation(const float fX, const float fY, const float fZ) {
    Mat4 ret;
    ret.f_[0] = 1.0f;
    ret.f_[4] = 0.0f;
    ret.f_[8] = 0.0f;
    ret.f_[12] = fX;
    ret.f_[1] = 0.0f;
    ret.f_[5] = 1.0f;
    ret.f_[9] = 0.0f;
    ret.f_[13] = fY;
    ret.f_[2] = 0.0f;
    ret.f_[6] = 0.0f;
    ret.f_[10] = 1.0f;
    ret.f_[14] = fZ;
    ret.f_[3] = 0.0f;
    ret.f_[7] = 0.0f;
    ret.f_[11] = 0.0f;
    ret.f_[15] = 1.0f;
    return ret;
}

Mat4 Mat4::Translation(const Vec3 vec) {
    Mat4 ret;
    ret.f_[0] = 1.0f;
    ret.f_[4] = 0.0f;
    ret.f_[8] = 0.0f;
    ret.f_[12] = vec.x_;
    ret.f_[1] = 0.0f;
    ret.f_[5] = 1.0f;
    ret.f_[9] = 0.0f;
    ret.f_[13] = vec.y_;
    ret.f_[2] = 0.0f;
    ret.f_[6] = 0.0f;
    ret.f_[10] = 1.0f;
    ret.f_[14] = vec.z_;
    ret.f_[3] = 0.0f;
    ret.f_[7] = 0.0f;
    ret.f_[11] = 0.0f;
    ret.f_[15] = 1.0f;
    return ret;
}

Mat4 Mat4::Perspective(float width, float height, float nearPlane, float farPlane) {
    float n2 = 2.0f * nearPlane;
    float rcpnmf = 1.f / (nearPlane - farPlane);

    Mat4 result;
    result.f_[0] = n2 / width;
    result.f_[4] = 0;
    result.f_[8] = 0;
    result.f_[12] = 0;
    result.f_[1] = 0;
    result.f_[5] = n2 / height;
    result.f_[9] = 0;
    result.f_[13] = 0;
    result.f_[2] = 0;
    result.f_[6] = 0;
    result.f_[10] = (farPlane + nearPlane) * rcpnmf;
    result.f_[14] = farPlane * rcpnmf * n2;
    result.f_[3] = 0;
    result.f_[7] = 0;
    result.f_[11] = -1.0f;
    result.f_[15] = 0;

    return result;
}

Mat4 Mat4::LookAt(const Vec3 &vec_eye, const Vec3 &vec_at, const Vec3 &vec_up) {
    Vec3 vec_forward, vec_up_norm, vec_side;
    Mat4 result;

    vec_forward.x_ = vec_eye.x_ - vec_at.x_;
    vec_forward.y_ = vec_eye.y_ - vec_at.y_;
    vec_forward.z_ = vec_eye.z_ - vec_at.z_;

    vec_forward.Normalize();
    vec_up_norm = vec_up;
    vec_up_norm.Normalize();
    vec_side = vec_up_norm.Cross(vec_forward);
    vec_up_norm = vec_forward.Cross(vec_side);

    result.f_[0] = vec_side.x_;
    result.f_[4] = vec_side.y_;
    result.f_[8] = vec_side.z_;
    result.f_[12] = 0;
    result.f_[1] = vec_up_norm.x_;
    result.f_[5] = vec_up_norm.y_;
    result.f_[9] = vec_up_norm.z_;
    result.f_[13] = 0;
    result.f_[2] = vec_forward.x_;
    result.f_[6] = vec_forward.y_;
    result.f_[10] = vec_forward.z_;
    result.f_[14] = 0;
    result.f_[3] = 0;
    result.f_[7] = 0;
    result.f_[11] = 0;
    result.f_[15] = 1.0;

    result.PostTranslate(-vec_eye.x_, -vec_eye.y_, -vec_eye.z_);
    return result;
}


double Mat4::length(float x, float y, float z) {
    return sqrt(x * x + y * y + z * z);
}

void Mat4::setLookAtM(float rm[], int rmOffset,
                      float eyeX, float eyeY, float eyeZ,
                      float centerX, float centerY, float centerZ, float upX, float upY,
                      float upZ) {

    // See the OpenGL GLUT documentation for gluLookAt for a description
    // of the algorithm. We implement it in a straightforward way:

    float fx = centerX - eyeX;
    float fy = centerY - eyeY;
    float fz = centerZ - eyeZ;

    // Normalize f
    float rlf = 1.0f / Mat4::length(fx, fy, fz);
    fx *= rlf;
    fy *= rlf;
    fz *= rlf;

    // compute s = f x up (x means "cross product")
    float sx = fy * upZ - fz * upY;
    float sy = fz * upX - fx * upZ;
    float sz = fx * upY - fy * upX;

    // and normalize s
    float rls = 1.0f / Mat4::length(sx, sy, sz);
    sx *= rls;
    sy *= rls;
    sz *= rls;

    // compute u = s x f
    float ux = sy * fz - sz * fy;
    float uy = sz * fx - sx * fz;
    float uz = sx * fy - sy * fx;

    rm[rmOffset + 0] = sx;
    rm[rmOffset + 1] = ux;
    rm[rmOffset + 2] = -fx;
    rm[rmOffset + 3] = 0.0f;

    rm[rmOffset + 4] = sy;
    rm[rmOffset + 5] = uy;
    rm[rmOffset + 6] = -fy;
    rm[rmOffset + 7] = 0.0f;

    rm[rmOffset + 8] = sz;
    rm[rmOffset + 9] = uz;
    rm[rmOffset + 10] = -fz;
    rm[rmOffset + 11] = 0.0f;

    rm[rmOffset + 12] = 0.0f;
    rm[rmOffset + 13] = 0.0f;
    rm[rmOffset + 14] = 0.0f;
    rm[rmOffset + 15] = 1.0f;

    Mat4::translateM(rm, rmOffset, -eyeX, -eyeY, -eyeZ);
}

void Mat4::translateM(float m[], int mOffset, float x, float y, float z) {
    for (int i = 0; i < 4; i++) {
        int mi = mOffset + i;
        m[12 + mi] += m[mi] * x + m[4 + mi] * y + m[8 + mi] * z;
    }
}

void Mat4::translateM(float tm[], int tmOffset, float m[], int mOffset, float x, float y, float z) {
    for (int i = 0; i < 12; i++) {
        tm[tmOffset + i] = m[mOffset + i];
    }
    for (int i = 0; i < 4; i++) {
        int tmi = tmOffset + i;
        int mi = mOffset + i;
        tm[12 + tmi] = m[mi] * x + m[4 + mi] * y + m[8 + mi] * z +
                       m[12 + mi];
    }
}

#define I(_i, _j) ((_j)+ 4*(_i))

void Mat4::multiplyMM(float r[], int resultOffset,
                      float lhs[], int lhsOffset,
                      float rhs[], int rhsOffset) {
    r = r + resultOffset;
    lhs = lhs + lhsOffset;
    rhs = rhs + rhsOffset;
    for (int i = 0; i < 4; i++) {
        const float rhs_i0 = rhs[I(i, 0)];
        float ri0 = lhs[I(0, 0)] * rhs_i0;
        float ri1 = lhs[I(0, 1)] * rhs_i0;
        float ri2 = lhs[I(0, 2)] * rhs_i0;
        float ri3 = lhs[I(0, 3)] * rhs_i0;
        for (int j = 1; j < 4; j++) {
            const float rhs_ij = rhs[I(i, j)];
            ri0 += lhs[I(j, 0)] * rhs_ij;
            ri1 += lhs[I(j, 1)] * rhs_ij;
            ri2 += lhs[I(j, 2)] * rhs_ij;
            ri3 += lhs[I(j, 3)] * rhs_ij;
        }
        r[I(i, 0)] = ri0;
        r[I(i, 1)] = ri1;
        r[I(i, 2)] = ri2;
        r[I(i, 3)] = ri3;
    }
}

static double PI = 3.14159265358979323846;

void Mat4::setRotateM(float rm[], int rmOffset, float a, float x, float y, float z) {
    rm[rmOffset + 3] = 0;
    rm[rmOffset + 7] = 0;
    rm[rmOffset + 11] = 0;
    rm[rmOffset + 12] = 0;
    rm[rmOffset + 13] = 0;
    rm[rmOffset + 14] = 0;
    rm[rmOffset + 15] = 1;
    a *= (float) (PI / 180.0f);
    float s = (float) sin(a);
    float c = (float) cos(a);
    if (1.0f == x && 0.0f == y && 0.0f == z) {
        rm[rmOffset + 5] = c;
        rm[rmOffset + 10] = c;
        rm[rmOffset + 6] = s;
        rm[rmOffset + 9] = -s;
        rm[rmOffset + 1] = 0;
        rm[rmOffset + 2] = 0;
        rm[rmOffset + 4] = 0;
        rm[rmOffset + 8] = 0;
        rm[rmOffset + 0] = 1;
    } else if (0.0f == x && 1.0f == y && 0.0f == z) {
        rm[rmOffset + 0] = c;
        rm[rmOffset + 10] = c;
        rm[rmOffset + 8] = s;
        rm[rmOffset + 2] = -s;
        rm[rmOffset + 1] = 0;
        rm[rmOffset + 4] = 0;
        rm[rmOffset + 6] = 0;
        rm[rmOffset + 9] = 0;
        rm[rmOffset + 5] = 1;
    } else if (0.0f == x && 0.0f == y && 1.0f == z) {
        rm[rmOffset + 0] = c;
        rm[rmOffset + 5] = c;
        rm[rmOffset + 1] = s;
        rm[rmOffset + 4] = -s;
        rm[rmOffset + 2] = 0;
        rm[rmOffset + 6] = 0;
        rm[rmOffset + 8] = 0;
        rm[rmOffset + 9] = 0;
        rm[rmOffset + 10] = 1;
    } else {
        float len = length(x, y, z);
        if (1.0f != len) {
            float recipLen = 1.0f / len;
            x *= recipLen;
            y *= recipLen;
            z *= recipLen;
        }
        float nc = 1.0f - c;
        float xy = x * y;
        float yz = y * z;
        float zx = z * x;
        float xs = x * s;
        float ys = y * s;
        float zs = z * s;
        rm[rmOffset + 0] = x * x * nc + c;
        rm[rmOffset + 4] = xy * nc - zs;
        rm[rmOffset + 8] = zx * nc + ys;
        rm[rmOffset + 1] = xy * nc + zs;
        rm[rmOffset + 5] = y * y * nc + c;
        rm[rmOffset + 9] = yz * nc - xs;
        rm[rmOffset + 2] = zx * nc - ys;
        rm[rmOffset + 6] = yz * nc + xs;
        rm[rmOffset + 10] = z * z * nc + c;
    }
}

void Mat4::setIdentityM(float sm[], int smOffset) {
    for (int i = 0; i < 16; i++) {
        sm[smOffset + i] = 0;
    }
    for (int i = 0; i < 16; i += 5) {
        sm[smOffset + i] = 1.0f;
    }
}