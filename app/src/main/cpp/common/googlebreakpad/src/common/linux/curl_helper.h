#ifndef __CURL_HELPER__
#define __CURL_HELPER__

#include <curl/curl.h>
#include <string>
#include <map>

class CurlHelper {
public:
    CurlHelper();

    ~CurlHelper();

    void Init();

    bool AddFile(const std::string &upload_file_path, const std::string &basename);

    bool SendRequest(const std::string &url,
                     const std::map<std::string, std::string> &parameters,
                     int *http_status_code,
                     std::string *http_header_data,
                     std::string *http_response_data);

private:
    CURL *curl_;

    struct curl_slist *headerlist_;
    struct curl_httppost *formpost_;
    struct curl_httppost *lastptr_;

};

#endif //__CURL_HELPER__