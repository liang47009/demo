#include "curl_helper.h"

CurlHelper::CurlHelper()
        : formpost_(NULL),
          lastptr_(NULL),
          headerlist_(NULL) {

}

CurlHelper::~CurlHelper() {

}

bool CurlHelper::AddFile(const std::string &upload_file_path, const std::string &basename) {
    curl_formadd(&formpost_, &lastptr_,
                 CURLFORM_COPYNAME, basename.c_str(),
                 CURLFORM_FILE, upload_file_path.c_str(),
                 CURLFORM_END);
    return true;
}

void CurlHelper::Init() {
    curl_ = curl_easy_init();
    // Disable 100-continue header.
    char buf[] = "Expect:";
    curl_slist_append(headerlist_, buf);
    curl_easy_setopt(curl_, CURLOPT_HTTPHEADER, headerlist_);
}

// Callback to get the response data from server.
static size_t WriteCallback(void *ptr, size_t size, size_t nmemb, void *userp) {
    if (!userp)
        return 0;

    std::string *response = reinterpret_cast<std::string *>(userp);
    size_t real_size = size * nmemb;
    response->append(reinterpret_cast<char *>(ptr), real_size);
    return real_size;
}

bool CurlHelper::SendRequest(const std::string &url,
                             const std::map<std::string, std::string> &parameters,
                             int *http_status_code,
                             std::string *http_header_data,
                             std::string *http_response_data) {
    curl_easy_setopt(curl_, CURLOPT_URL, url.c_str());
    std::map<std::string, std::string>::const_iterator iter = parameters.begin();
    for (; iter != parameters.end(); ++iter)
        curl_formadd(&formpost_, &lastptr_,
                     CURLFORM_COPYNAME, iter->first.c_str(),
                     CURLFORM_COPYCONTENTS, iter->second.c_str(),
                     CURLFORM_END);

    curl_easy_setopt(curl_, CURLOPT_HTTPPOST, formpost_);
    if (http_response_data != NULL) {
        http_response_data->clear();
        curl_easy_setopt(curl_, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl_, CURLOPT_WRITEDATA,
                         reinterpret_cast<void *>(http_response_data));
    }
    if (http_header_data != NULL) {
        http_header_data->clear();
        curl_easy_setopt(curl_, CURLOPT_HEADERFUNCTION, WriteCallback);
        curl_easy_setopt(curl_, CURLOPT_HEADERDATA,
                         reinterpret_cast<void *>(http_header_data));
    }

    CURLcode err_code = curl_easy_perform(curl_);
    if (http_status_code != NULL) {
        curl_easy_getinfo(curl_, CURLINFO_RESPONSE_CODE, http_status_code);
    }

#ifndef NDEBUG
    if (err_code != CURLE_OK)
        fprintf(stderr, "Failed to send http request to %s, error: %s\n",
                url.c_str(),
                curl_easy_strerror(err_code));
#endif
    if (headerlist_ != NULL) {
        curl_slist_free_all(headerlist_);
    }

    curl_easy_cleanup(curl_);
    if (formpost_ != NULL) {
        curl_formfree(formpost_);
    }

    return err_code == CURLE_OK;
}