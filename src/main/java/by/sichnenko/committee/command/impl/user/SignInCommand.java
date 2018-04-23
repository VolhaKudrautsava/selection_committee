package by.sichnenko.committee.command.impl.user;

import by.sichnenko.committee.command.ActionCommand;
import by.sichnenko.committee.constant.GeneralConstant;
import by.sichnenko.committee.controller.SessionRequestContent;
import by.sichnenko.committee.exception.ServiceException;
import by.sichnenko.committee.model.User;
import by.sichnenko.committee.service.UserService;
import by.sichnenko.committee.service.impl.UserServiceImpl;
import by.sichnenko.committee.type.RouterType;
import by.sichnenko.committee.util.Router;
import java.util.Set;

import static by.sichnenko.committee.constant.PageNameConstant.*;
import static by.sichnenko.committee.constant.RequestNameConstant.LOGIN;
import static by.sichnenko.committee.constant.RequestNameConstant.ROLE;
import static by.sichnenko.committee.constant.RequestNameConstant.USER;

public class SignInCommand implements ActionCommand {
    @Override
    public Router execute(SessionRequestContent sessionRequestContent) {
        UserService userService = new UserServiceImpl();
        User authenticatedUser;
        try {
            authenticatedUser = userService.signIn(sessionRequestContent);
        } catch (ServiceException e) {
            Set<String> keys = sessionRequestContent.getRequestAttributes().keySet();

            if (keys.contains(GeneralConstant.INCORRECT_DATA)) {
                return new Router(RouterType.FORWARD, SIGN_IN_PAGE);
            }
            return new Router(RouterType.REDIRECT, MAIN_PAGE);
        }
        if (authenticatedUser != null) {
            sessionRequestContent.getSessionAttributes().put(USER, authenticatedUser);
            sessionRequestContent.getSessionAttributes().put(LOGIN, authenticatedUser.getLogin());
            sessionRequestContent.getSessionAttributes().put(ROLE, authenticatedUser.getRole());
            return new Router(RouterType.REDIRECT, MAIN_PAGE);
        }
        return new Router(RouterType.REDIRECT, ERROR_PAGE);
    }
}