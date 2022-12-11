package ua.kotsko.project.Examinator.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kotsko.project.Examinator.dto.MediaDTO;
import ua.kotsko.project.Examinator.dto.PostDTO;
import ua.kotsko.project.Examinator.models.Media;
import ua.kotsko.project.Examinator.models.Post;
import ua.kotsko.project.Examinator.models.User;
import ua.kotsko.project.Examinator.repository.PostRepository;
import ua.kotsko.project.Examinator.services.PostService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private static final String ROLE_TEACHER = "ROLE_TEACHER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ALL = "FOR_ALL";
    private static final String STAFF_ONLY = "STAFF_ONLY";

    @Autowired
    private PostRepository postRepository;

    private List<PostDTO> getPostsRestrictionAndDateRange(String restriction, Date currentDate) {
        List<Post> postList  = postRepository.searchAllByRestriction(restriction);
        List<PostDTO> postDTOList = postList.stream()
                .filter(post -> this.filterDateRange(post, currentDate))
                .map(this::convertToPostDTO)
                .collect(Collectors.toList());
        return postDTOList;
    }

    private PostDTO convertToPostDTO(Post post) {
        return PostDTO.builder().
                id(post.getId()).
                title(post.getTitle()).
                content(post.getContent()).
                dateFrom(this.formatDate(Objects.nonNull(post.getFrom()) ? post.getFrom() : post.getCreationDate())).
                firstNameCreator(post.getCreator().getName()).
                lastNameCreator(post.getCreator().getSurname()).
                image(this.convertToMediaDTO(post.getImage())).
                build();
    }

    private MediaDTO convertToMediaDTO(Media media) {
        return MediaDTO.builder().
                id(media.getId()).
                url(media.getUrl()).
                width(media.getWidth()).
                height(media.getHeight()).
                type(media.getType().getType()).
                build();
    }

    private String formatDate(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("'From: ' dd.MM.yyyy HH:mm:ss");
        return formater.format(date);
    }

    private boolean filterDateRange(Post post, Date currentDate) {
        boolean flag = false;

        Date from = post.getFrom();
        Date to = post.getTo();

        if (from == null && to == null) {
            flag = true;
        } else if (from == null) {
            flag = to.after(currentDate);
        } else if (to == null) {
            flag = from.before(currentDate);
        } else {
            flag = from.before(currentDate) && to.after(currentDate);
        }

        return flag;
    }

    @Override
    public List<PostDTO> getAllPostForUser(User user, Date currentDate) {
        List<PostDTO> postDTO = getPostsRestrictionAndDateRange(ALL, currentDate);
        if (user.getRoles().
                stream().
                anyMatch(role -> role.getName().equals(ROLE_TEACHER)
                || role.getName().equals(ROLE_ADMIN))) {
            postDTO.addAll(getPostsRestrictionAndDateRange(STAFF_ONLY, currentDate));
        }
        postDTO.sort(Comparator.comparing(PostDTO::getId));
        return postDTO;
    }

    @Override
    public Post getPostByID(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElseThrow();
    }

    @Override
    public PostDTO getPostDTOById(Long id) {
        Post post = getPostByID(id);
        return convertToPostDTO(post);
    }
}
