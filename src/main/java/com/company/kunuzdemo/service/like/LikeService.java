package com.company.kunuzdemo.service.like;

import com.company.kunuzdemo.dtos.request.LIkeRequestDTO;
import com.company.kunuzdemo.dtos.response.LikeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface LikeService {

    LikeResponseDTO create(LIkeRequestDTO lIkeRequestDTO);

    LikeResponseDTO getByID(UUID likeID);

    List<LikeResponseDTO> getLikeByArticleID(UUID articleID, Integer page, Integer size);

    List<LikeResponseDTO> getDislikeByArticleID(UUID articleID, Integer page, Integer size);

    void deleteByID(UUID likeID);
}
