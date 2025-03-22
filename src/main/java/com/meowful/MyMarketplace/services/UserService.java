package com.meowful.MyMarketplace.services;

import com.meowful.MyMarketplace.models.Role;
import com.meowful.MyMarketplace.models.ShoppingCart;
import com.meowful.MyMarketplace.models.User;
import com.meowful.MyMarketplace.repositories.ShoppingCartRepository;
import com.meowful.MyMarketplace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartRepository shoppingCartRepository;
    private final MailSenderService mailSenderService;


    public void addUser(User user) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.user));
            user.setActivationCode(null);
            user.setActive(true);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCartRepository.save(shoppingCart);

            // Привязка корзины к пользователю
            user.setShoppingCart(shoppingCart);

            // Сохранение пользователя в базе данных
            userRepository.save(user);
        }

    public User getUser(Principal principal) {
        if (principal == null) return new User();
        else return userRepository.findByEmail(principal.getName()).get();
    }

    public boolean emailValidation(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()) return false;
        else{

            user.setActivationCode(UUID.randomUUID().toString());

            String body = String.format("""
                            Hello, %s
                            Your verification link:
                            http://localhost:8080/activation/%s
                            """,
                    user.getName(), user.getActivationCode());

            mailSenderService.send(user.getEmail(), "Verification link", body);
            return true;
        }
    }
    public boolean codeValidation(String code, User user){
        return (user != null) && user.getActivationCode().equals(code);
    }
}
