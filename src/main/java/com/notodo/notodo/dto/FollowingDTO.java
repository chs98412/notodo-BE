package com.notodo.notodo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FollowingDTO {
    private String email;
    private String nickname;

    private String thumbnail;
    private boolean isFollower;
}
