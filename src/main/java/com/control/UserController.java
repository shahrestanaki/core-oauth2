package com.control;


import com.dozer.entity.UsersDozer;
import com.model.entity.User;
import com.service.entity.UserService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RestController
@EnableAutoConfiguration
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Principal> get(final Principal principal) {
        return ResponseEntity.ok(principal);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String list(@RequestParam("row") String row, @RequestParam("page") String page, @RequestParam("filter") String filter, @RequestParam("order") String order, @RequestParam("orderType") String orderType) {
        System.out.println("*********** list : " + row + "-" + page + "-" + filter + "-" + order + "-" + orderType);
        return "list1";
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<User> list() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/save")
    public User save(@Valid @RequestBody User user) {
        userService.save(user);
        return user;
    }

    @PutMapping(value = "/edit")
    //@ResponseBody -> because use @RestController
    public UsersDozer edit(@Valid @RequestBody User user) {
        userService.edit(user);
        UsersDozer dozer = mapper.map(user, UsersDozer.class);
        return dozer;
    }

    /*public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(Collectors.toList())
        );
        return ResponseEntity.ok(model);
    }*/

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String currentUser2() {
        return "{\"id\":\"1\"}";
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String myuser() {
        return "{\"id\":\"2\"}";
    }
}
