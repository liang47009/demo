#include <jni.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
#include "app.h"


void Func(char str[3]) {
    int len = sizeof(str);
    LOGI("len: %d", len);
    LOGI("str: %s", str);
    void *p = malloc(100);
    len = sizeof(p);
    LOGI("len: %d", len);
}

void Test_Test(void) {
    LOGI("Test_Test");
}

extern "C"
{

JNIEXPORT void JNICALL Java_com_yunfeng_nativefork_MainActivity_fork(JNIEnv *env, jclass type) {
    pid_t fpid; //fpid表示fork函数返回的值
    int count = 0;
    fpid = fork();
    if (fpid < 0) {
        LOGI("error in fork!");
    } else if (fpid == 0) {
        LOGI("i am the child process, my process id is %d, fpid: %d", getpid(), fpid);
        count++;
    } else {
        LOGI("i am the parent process, my process id is %d, fpid: %d", getpid(), fpid);
        count++;
    }
    LOGI("统计结果是: %d", count);
}

//void GetMemory(char *p) {
//    p = (char *) malloc(100);
//}
//void Test(void) {
//    char *str = NULL;
//    GetMemory(str);
//    strcpy(str, "hello world");
//    LOGI("str: %s", str);
//}
//char *GetMemory(void) {
//    char p[] = "hello world";
//    return p;
//}
//void Test(void) {
//    char *str = NULL;
//    str = GetMemory();
//    LOGI("str: %s", str);
//}
//void GetMemory2(char **p, size_t num) {
//    *p = (char *) malloc(num);
//}
//void Test(void) {
//    char *str = NULL;
//    GetMemory2(&str, 100);
//    strcpy(str, "hello");
//    LOGI("str: %s", str);
//}
void Test(int i) {
    LOGI("cr: %d", i);
    clazz->test("sadf");
    char *str = (char *) malloc(6);
    char *cr = (char *) malloc(1);
    LOGI("str: %p", str);
    LOGI("cr: %p", cr);

    strcpy(str, "hello");
    strcpy(cr, "123");
    free(str);
    free(cr);
    if (str != NULL) {
        strcpy(cr, "3244546");
        LOGI("str: %s", cr);
        strcpy(str, "hello world");
        LOGI("str: %s", str);
    }
}
/**
 * strcpy - Copy a %NUL terminated string
 * @dest: Where to copy the string to
 * @src: Where to copy the string from
 */
char *strcpy_custom1(char *dest, const char *src) {
    char *tmp = dest;

    while ((*dest++ = *src++) != '\0')
        /* nothing */;
    return tmp;
}

char *strcpy_custom(char *dst, const char *src) {
    char *q = dst;
    const char *p = src;
    char ch;

    do {
        *q++ = ch = *p++;
    } while (ch);

    return dst;
}

int getMillisecond() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

JNIEXPORT void JNICALL Java_com_yunfeng_nativefork_MainActivity_test(JNIEnv *env, jclass type) {
    char str[] = "Hellocxvklsahfildsajofjas;lfj lkasdjfklqwyufoiweqfj slakdjf klsdufieowfuyewoihflksafhjlkdsajf woieyfweoih flkesah flkesaj foieawuf oiwjflekwaj fweoiufiwoeyf ieowhaflkewjafi lweuafiow ahfoiwha flk";
    char *p = str;
    int n = 10;
    int len = sizeof(str);
    LOGI("len: %d", len);
    len = sizeof(p);
    LOGI("len: %d", len);
    len = sizeof(n);
    LOGI("len: %d", len);
    Func(str);
//    Test();
    {
        int start = getMillisecond();
        p = strcpy_custom(str, p);
        int end = getMillisecond();
        int t = end - start;
        LOGI("strcpy_custom: %p, t: %d", p, t);
    }
    {
        int start = getMillisecond();
        p = strcpy_custom1(str, p);
        int end = getMillisecond();
        int t = end - start;
        LOGI("strcpy_custom: %p, t: %d", p, t);
    }
}

void inplace_swap(int *x, int *y) {
    *y = *x ^ *y;
    *x = *x ^ *y;
    *y = *x ^ *y;
}

void reverse_array(int array[], int cnt) {
    int first, last;
    for (first = 0, last = cnt - 1;
         first < last;
         first++, last--) {
        inplace_swap(&array[first], &array[last]);
        LOGI("reverse_array---> first:%d, last:%d", first, last);
    }
}

JNIEXPORT void JNICALL Java_com_yunfeng_nativefork_MainActivity_math_1game(JNIEnv *, jclass) {
//    int x, y;
//    x = 5;
//    y = 8;
//    LOGI("math game---> x:%d, y:%d", x, y);
//    inplace_swap(&x, &y);
//    LOGI("math game---> x:%d, y:%d", x, y);
    int array[6] = {6, 5, 4, 3, 2, 1};
    reverse_array(array, 6);
    for (int i = 0; i < 6; ++i) {
        LOGI("math game---> array[i]: %d", array[i]);
    }
}

void game_start(void) {

}

}
