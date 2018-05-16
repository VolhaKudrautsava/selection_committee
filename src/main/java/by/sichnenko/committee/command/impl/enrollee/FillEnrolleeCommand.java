package by.sichnenko.committee.command.impl.enrollee;

import by.sichnenko.committee.command.ActionCommand;
import by.sichnenko.committee.constant.GeneralConstant;
import by.sichnenko.committee.controller.SessionRequestContent;
import by.sichnenko.committee.exception.ServiceException;
import by.sichnenko.committee.model.Enrollee;
import by.sichnenko.committee.service.EnrolleeService;
import by.sichnenko.committee.service.impl.EnrolleeServiceImpl;
import by.sichnenko.committee.type.RouterType;
import by.sichnenko.committee.util.Router;

import java.util.Set;

import static by.sichnenko.committee.constant.RequestNameConstant.ENROLLEE;

public class FillEnrolleeCommand implements ActionCommand {
    @Override
    public Router execute(SessionRequestContent sessionRequestContent) {
        EnrolleeService enrolleeService = new EnrolleeServiceImpl();
        try {
            Enrollee enrollee = enrolleeService.fillEnrollee(sessionRequestContent);
            sessionRequestContent.getSessionAttributes().put(ENROLLEE, enrollee);
        } catch (ServiceException e) {
            Set<String> keys = sessionRequestContent.getRequestAttributes().keySet();

            if (keys.contains(GeneralConstant.INCORRECT_DATA)) {
                return new Router(RouterType.FORWARD, defineQuery(sessionRequestContent));
            }
            return new Router(RouterType.ERROR);
        }
        return new Router(RouterType.REDIRECT, defineQuery(sessionRequestContent));
    }
}
