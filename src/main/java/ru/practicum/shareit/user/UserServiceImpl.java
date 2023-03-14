package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.error.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Transactional
    @Override
    public User createUser(User user) {
        List<User> usersTemp = getAllUsers();

//        for (User i : usersTemp) {
//            if (i.getEmail().equals(user.getEmail()) && user.getId() != i.getId()) {
//                repository.save(user);
//                throw new AlreadyExistException("email(id = " + user.getId() + ") already exist");
//            }
//        }
        User temp = repository.save(user);
        return temp;
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {

//        List<User> usersTemp = getAllUsers();
//        for (User i : usersTemp) {
//            if (i.getEmail().equals(user.getEmail()) && user.getId() != i.getId()) {
//                throw new AlreadyExistException("email(id = " + user.getId() + ") already exist");
//            }
//        }


        user.setId(id);
        User userTemp = updateParameters(user);
        User userResult = getUser(id);

        userResult.setId(userTemp.getId());
        userResult.setName(userTemp.getName());
        userResult.setEmail(userTemp.getEmail());

        return userResult;
    }

    @Transactional
    @Override
    public User getUser(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(""));
        return user;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    private User updateParameters(User user) {

        for (int i = 0; i < repository.findAll().size(); i++) {
            if (repository.findAll().get(i).getId().equals(user.getId())) {
                if (user.getName() == null) {
                    user.setName(repository.findAll().get(i).getName());
                }
                if (user.getEmail() == null) {
                    user.setEmail(repository.findAll().get(i).getEmail());
                }
            }
        }
        return user;
    }
//    private void checkExist(User user) {
//
//        if (repository.existence(user.getId())) {
//            throw new NotFountException("object wish id = " + user.getId() + " not exist");
//        }
//        for (Integer i : repository.getUsersMap().keySet()) {
//            if (repository.getUsersMap().get(i).getEmail().equals(user.getEmail()) && user.getId() != i) {
//                throw new AlreadyExistException("email(id = " + user.getId() + ") already exist");
//            }
//        }
//    }
}
