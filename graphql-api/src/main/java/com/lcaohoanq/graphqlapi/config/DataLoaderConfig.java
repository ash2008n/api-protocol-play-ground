package com.lcaohoanq.graphqlapi.config;

import com.lcaohoanq.graphqlapi.entity.Post;
import com.lcaohoanq.graphqlapi.entity.User;
import com.lcaohoanq.graphqlapi.repository.PostRepository;
import com.lcaohoanq.graphqlapi.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import reactor.core.publisher.Mono;

import java.util.ArrayList; // Imported for the ArrayList usage
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataLoaderConfig {
    public DataLoaderConfig(BatchLoaderRegistry registry, UserRepository userRepository, PostRepository postRepository) {

        // DataLoader for User (No changes needed here)
        registry.forTypePair(Long.class, User.class)
            .registerMappedBatchLoader((userIds, environment) -> {
                List<User> users = userRepository.findAllById(userIds);
                Map<Long, User> userMap = new HashMap<>();
                users.forEach(user -> userMap.put(user.getId(), user));
                return Mono.just(userMap);
            });

        // DataLoader for Posts (Fixed)
        registry.forTypePair(Long.class, List.class)
            .withName("postsByUserIdLoader")
            .registerMappedBatchLoader((userIds, environment) -> {
                List<Post> posts = postRepository.findAll(); // Note: Consider filtering by userIds here for performance!

                // Keep generic type for safety during processing
                Map<Long, List<Post>> postsMap = new HashMap<>();

                posts.forEach(post -> {
                    Long userId = post.getUser().getId();
                    postsMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(post);
                });

                // FIX: Cast to raw Map or (Map<Long, List>) to satisfy the TypePair
                return Mono.just((Map) postsMap);
            });
    }
}