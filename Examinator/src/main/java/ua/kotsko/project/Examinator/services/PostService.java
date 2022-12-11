package ua.kotsko.project.Examinator.services;

import ua.kotsko.project.Examinator.dto.PostDTO;
import ua.kotsko.project.Examinator.models.Post;
import ua.kotsko.project.Examinator.models.User;

import java.util.Date;
import java.util.List;

public interface PostService {

    List<PostDTO> getAllPostForUser(User user, Date currentDate);

    Post getPostByID(Long id);

    PostDTO getPostDTOById(Long id);
}
