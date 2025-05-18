package controller;

import model.User;
import view.UserProfileView;

public class UserProfileController {

    private UserProfileView view;
    private User user;

    public UserProfileController(User user) {
        this.user = user;
        this.view = new UserProfileView(user);
        view.setVisible(true);
    }
}
