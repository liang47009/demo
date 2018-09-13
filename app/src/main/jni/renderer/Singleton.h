#ifndef _SINGLETON_H_
#define _SINGLETON_H_

#include <stdlib.h>

class noncopyable {
protected:
    noncopyable() {};

    virtual ~noncopyable() {};
private:
    noncopyable(const noncopyable &);

    noncopyable &operator=(const noncopyable &);
};

template<typename T, bool destroy_on_exit = true>
class Singleton : noncopyable {
    typedef T instance_type;
    typedef instance_type *instance_pointer;
    typedef volatile instance_pointer volatile_instance_pointer;
    static volatile_instance_pointer m_instance;

    static void destroy();

public:
    inline static instance_pointer getInstance() {

        if (!m_instance) {
            m_instance = new instance_type();
            if (destroy_on_exit) {
                ::atexit(destroy);
            }
        }
        return m_instance;
    }

    inline static void destroyInstance() {
        if (!destroy_on_exit) {
            destroy();
        }
    }
};

template<typename T, bool destroy_on_exit>
void Singleton<T, destroy_on_exit>::destroy() {
    typedef char T_must_be_complete_type[sizeof(T) == 0 ? -1 : 1];
    T_must_be_complete_type dummy;
    (void) dummy;
    delete m_instance;
    m_instance = nullptr;
}

template<typename T, bool destroy_on_exit>
typename Singleton<T, destroy_on_exit>::volatile_instance_pointer
        Singleton<T, destroy_on_exit>::m_instance = nullptr;


#endif//