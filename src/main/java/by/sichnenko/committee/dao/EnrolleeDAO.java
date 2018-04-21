package by.sichnenko.committee.dao;

import by.sichnenko.committee.exception.DAOException;
import by.sichnenko.committee.model.Enrollee;

public interface EnrolleeDAO extends DAO<Enrollee> {
    void changeAllEnrolleesStatus(Long oldStatusId, Long newStatusId) throws DAOException;

    void changeStatus(Long enrolleeId, Long newStatusId) throws DAOException;

    Enrollee findEnrolleeByUserId(Long userId) throws DAOException;
}