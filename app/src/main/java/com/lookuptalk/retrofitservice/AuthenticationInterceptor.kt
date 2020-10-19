package com.lookuptalk.retrofitservice

import android.content.Context
import com.lookuptalk.utils.UserSession
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", UserSession(context).getAppToken())
                .build()
        )
    }
//    appToken =
//    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c3ItYzM1NTc3OTctYmM3YS00MTQ4LWI5NmItZjdlMzgxOTAxNjdlIiwidXNlclVuaXF1ZUlkIjoidW5pcS05YmEwZGI2Ny1hNjMzLTRjNmQtYjUxNC1lODcyZDk4YWU5ODMiLCJpYXQiOjE2MDE5MTY1OTEsImV4cCI6MTYwMjUyMTM5MX0.Lz_ySCI2wQtXIOmMsk097MA5trxKKA05yO6mzWg6fOY"

}