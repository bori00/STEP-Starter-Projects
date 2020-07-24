// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import org.jetbrains.annotations.Nullable;


/** This servlet handles user's data: doGet() checks if the user is authenticated 
 * and redirects them to a log in page if needed*/
@WebServlet("/user-data")
public class UserServlet extends HttpServlet {

    private static class UserLoginData{
        private boolean isLoggedIn;
        @Nullable private String loginUrl;

        private UserLoginData(boolean isLoggedIn, String loginUrl){
            this.isLoggedIn = isLoggedIn;
            this.loginUrl = loginUrl;
        }

        public static UserLoginData generateUserLoginData(){
            UserService userService = UserServiceFactory.getUserService();
            boolean isUserLoggedIn = userService.isUserLoggedIn();
            String myLoginUrl = null;
            if(!isUserLoggedIn){
                myLoginUrl = userService.createLoginURL("/contact.html");
            }
            return new UserLoginData(isUserLoggedIn, myLoginUrl);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
        response.setContentType("application/json;");
        UserLoginData currentUserLoginData = UserLoginData.generateUserLoginData();
        response.getWriter().println(convertToJsonUsingGson(currentUserLoginData));
    }

    private String convertToJsonUsingGson(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }
}