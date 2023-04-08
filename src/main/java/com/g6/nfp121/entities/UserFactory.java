package com.g6.nfp121.entities;

import com.g6.nfp121.models.user.UserSignupModel;

public class UserFactory {

    public static User create(UserSignupModel userSignupModel) {
        return new User(userSignupModel.name, userSignupModel.firstName, userSignupModel.email,
                userSignupModel.password);
    }

}
