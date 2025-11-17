package com.lcaohoanq.graphqlapi.controller;

import com.lcaohoanq.graphqlapi.dto.CreatePostInput;
import com.lcaohoanq.graphqlapi.dto.CreateUserInput;
import com.lcaohoanq.graphqlapi.dto.UpdateUserInput;
import com.lcaohoanq.graphqlapi.entity.Post;
import com.lcaohoanq.graphqlapi.entity.User;
import com.lcaohoanq.graphqlapi.repository.PostRepository;
import com.lcaohoanq.graphqlapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class GraphQLController {
    
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    
    // ========== QUERIES ==========
    
    @QueryMapping
    public List<User> users() {
        return userRepository.findAll();
    }
    
    @QueryMapping
    public User user(@Argument Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    @QueryMapping
    public List<Post> posts() {
        return postRepository.findAll();
    }
    
    @QueryMapping
    public Post post(@Argument Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }
    
    @QueryMapping
    public List<Post> postsByUserId(@Argument Long userId) {
        return postRepository.findByUserId(userId);
    }
    
    // ========== MUTATIONS ==========
    
    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setBio(input.getBio());
        return userRepository.save(user);
    }
    
    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UpdateUserInput input) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setBio(input.getBio());
        
        return userRepository.save(user);
    }
    
    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return true;
    }
    
    @MutationMapping
    public Post createPost(@Argument CreatePostInput input) {
        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + input.getUserId()));
        
        Post post = new Post();
        post.setTitle(input.getTitle());
        post.setContent(input.getContent());
        post.setUser(user);
        
        return postRepository.save(post);
    }
    
    // ========== FIELD RESOLVERS (with DataLoader) ==========
    
    @SchemaMapping(typeName = "Post", field = "author")
    public User author(Post post) {
        // DataLoader sẽ tự động batch load users
        return post.getUser();
    }
    
    @SchemaMapping(typeName = "Post", field = "userId")
    public Long userId(Post post) {
        return post.getUser().getId();
    }
    
    // BatchMapping để load posts cho nhiều users cùng lúc (chống N+1)
    @BatchMapping(typeName = "User", field = "posts")
    public Map<User, List<Post>> posts(List<User> users) {
        List<Long> userIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
        
        List<Post> posts = postRepository.findAll().stream()
                .filter(post -> userIds.contains(post.getUser().getId()))
                .collect(Collectors.toList());
        
        return users.stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user -> posts.stream()
                                .filter(post -> post.getUser().getId().equals(user.getId()))
                                .collect(Collectors.toList())
                ));
    }
}
