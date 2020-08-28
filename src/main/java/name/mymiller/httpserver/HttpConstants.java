/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.httpserver;

/**
 * @author jmiller Constants used by the HTTP Server
 */
public class HttpConstants {
    /**
     *
     */
    public static final int DELAY_TIME = 10;

    /* HTTP Request Types */
    public static final String DELETE_REQUEST = "DELETE";
    public static final String GET_REQUEST = "GET";
    public static final String HEAD_REQUEST = "HEAD";
    public static final String POST_REQUEST = "POST";
    public static final String PUT_REQUEST = "PUT";

    /* HTTP Response Codes */

    public final static int HTTP_CONTINUE_STATUS = 100;
    public final static int HTTP_SWITCHING_PROTOCOLS_STATUS = 101;
    public final static int HTTP_PROCESSING_STATUS = 102;

    public static final int HTTP_OK_STATUS = 200;
    public final static int HTTP_CREATED_STATUS = 201;
    public final static int HTTP_ACCEPTED_STATUS = 202;
    public final static int HTTP_NON_AUTHORITATIVE_INFORMATION_STATUS = 203;
    public final static int HTTP_NO_CONTENT_STATUS = 204;
    public final static int HTTP_RESET_CONTENT_STATUS = 205;
    public final static int HTTP_PARTIAL_CONTENT_STATUS = 206;
    public final static int HTTP_MULTI_STATUS_STATUS = 207;

    public final static int HTTP_MULTIPLE_CHOICES_STATUS = 300;
    public final static int HTTP_MOVED_PERMANENTLY_STATUS = 301;
    public final static int HTTP_MOVED_TEMPORARILY_STATUS = 302;
    public final static int HTTP_FOUND_STATUS = 302;
    public final static int HTTP_SEE_OTHER_STATUS = 303;
    public final static int HTTP_NOT_MODIFIED_STATUS = 304;
    public final static int HTTP_USE_PROXY_STATUS = 305;
    public final static int HTTP_TEMPORARY_REDIRECT_STATUS = 307;

    public final static int HTTP_BAD_REQUEST_STATUS = 400;
    public final static int HTTP_UNAUTHORIZED_STATUS = 401;
    public final static int HTTP_PAYMENT_REQUIRED_STATUS = 402;
    public final static int HTTP_FORBIDDEN_STATUS = 403;
    public final static int HTTP_NOT_FOUND_STATUS = 404;
    public final static int HTTP_METHOD_NOT_ALLOWED_STATUS = 405;
    public final static int HTTP_NOT_ACCEPTABLE_STATUS = 406;
    public final static int HTTP_PROXY_AUTHENTICATION_REQUIRED_STATUS = 407;
    public final static int HTTP_REQUEST_TIMEOUT_STATUS = 408;
    public final static int HTTP_CONFLICT_STATUS = 409;
    public final static int HTTP_GONE_STATUS = 410;
    public final static int HTTP_LENGTH_REQUIRED_STATUS = 411;
    public final static int HTTP_PRECONDITION_FAILED_STATUS = 412;
    public final static int HTTP_REQUEST_ENTITY_TOO_LARGE_STATUS = 413;
    public final static int HTTP_REQUEST_URI_TOO_LONG_STATUS = 414;
    public final static int HTTP_UNSUPPORTED_MEDIA_TYPE_STATUS = 415;
    public final static int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE_STATUS = 416;
    public final static int HTTP_EXPECTATION_FAILED_STATUS = 417;
    public final static int HTTP_UNPROCESSABLE_ENTITY_STATUS = 422;
    public final static int HTTP_LOCKED_STATUS = 423;
    public final static int HTTP_FAILED_DEPENDENCY_STATUS = 424;

    public final static int HTTP_INTERNAL_SERVER_ERROR_STATUS = 500;
    public final static int HTTP_NOT_IMPLEMENTED_STATUS = 501;
    public final static int HTTP_BAD_GATEWAY_STATUS = 502;
    public final static int HTTP_SERVICE_UNAVAILABLE_STATUS = 503;
    public final static int HTTP_GATEWAY_TIMEOUT_STATUS = 504;
    public final static int HTTP_HTTP_VERSION_NOT_SUPPORTED_STATUS = 505;
    public final static int HTTP_INSUFFICIENT_STORAGE_STATUS = 507;

    /* HTTP DELIMITERS */
    public static final String AND_DELIMITER = "&";
    public static final String EQUAL_DELIMITER = "=";

}
