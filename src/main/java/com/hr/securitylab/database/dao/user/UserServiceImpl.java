package com.hr.securitylab.database.dao.user;

import com.hr.securitylab.database.entities.hibernate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Joost on 14-10-2016.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl() {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findUserByUsername(String username){
        return userDao.findUserByUsername(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(User user) {
        userDao.saveOrUpdate(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean checkIfEmailExists(String email) {
        return userDao.checkIfEmailExists(email);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean checkIfUsernameExists(String username) {
        return userDao.checkIfUsernameExists(username);
    }

}
