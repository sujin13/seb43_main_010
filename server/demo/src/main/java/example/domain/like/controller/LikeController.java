package example.domain.like.controller;

import example.domain.like.dto.LikeRequestDto;
import example.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import example.global.common.global.HttpResponseEntity.ResponseError.ResponseResult;

import javax.validation.Valid;

import static example.global.common.global.HttpResponseEntity.success;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LikeController {
    private final LikeService likeService;


    // feedPost 좋아요 기능 (fan, artist 둘다 가능)
    @PostMapping("feed/{groupId}/{feedPostId}/like")
    public ResponseResult<?> insert(@PathVariable("groupId") Integer groupId,
                                    @PathVariable("feedPostId") Integer feedPostId,
                                    @RequestBody @Valid LikeRequestDto likeRequestDto) throws Exception {
        if (likeRequestDto.getFanId() != null) {
            // fanLikeRequestDto 처리
            // fanLikeRequestDto에 feedPostId 설정
            likeRequestDto.setFeedPostId(feedPostId);
            likeService.insertFanLike(groupId, likeRequestDto);
        } else if (likeRequestDto.getArtistId() != null) {
            // artistLikeRequestDto 처리
            // artistLikeRequestDto에 artistPostId 설정
            likeRequestDto.setArtistPostId(feedPostId);
            likeService.insertArtistLike(groupId, likeRequestDto);
        } else {
            // fanId나 artistId가 둘 다 null인 경우에 대한 처리
            throw new IllegalArgumentException("Invalid request. Either fanId or artistId should be provided.");
        }

        // 성공 응답 반환
        return success(null);
    }
//    @PostMapping("/{feedPostId}")
//    public ResponseResult<?> insert(@PathVariable("feedPostId") Integer feedPostId,
//                                    @RequestBody @Valid LikeRequestDto LikeRequestDto)throws Exception{
//        // fanLikeRequestDto에 feedPostId 설정
//        LikeRequestDto.setFeedPostId(feedPostId);
//
//        // 좋아요 기능을 처리하는 likeService.insert() 메서드 호출
//        likeService.insert(LikeRequestDto);
//
//        // 성공 응답 반환
//        return success(null);
//    }

    // artistPost 좋아요 기능 (fan, artist 둘다 가능)

    @PostMapping("/artist/{artistPostId}/like")
    public ResponseResult<?> likeArtistPost(@PathVariable("groupId") Integer groupId,
                                            @PathVariable("artistPostId") Integer artistPostId,
                                            @RequestBody @Valid LikeRequestDto likeRequestDto) throws Exception {
        if (likeRequestDto.getFanId() != null) {
            // fanLikeRequestDto 처리
            // fanLikeRequestDto에 artistPostId 설정
            likeRequestDto.setArtistPostId(artistPostId);
            likeService.insertFanLike(groupId,likeRequestDto);
        } else if (likeRequestDto.getArtistId() != null) {
            // artistLikeRequestDto 처리
            // artistLikeRequestDto에 artistPostId 설정
            likeRequestDto.setArtistPostId(artistPostId);
            likeService.insertArtistLike(groupId, likeRequestDto);
        } else {
            // fanId나 artistId가 둘 다 null인 경우에 대한 처리
            throw new IllegalArgumentException("Invalid request. Either fanId or artistId should be provided.");
        }

        // 성공 응답 반환
        return success(null);
    }
//    @PostMapping("/artistPost")
//    public ResponseResult<?> insert(@PathVariable("artistPostId") Integer artistPostId,
//                                    @RequestBody @Valid ArtistLikeRequestDto artistLikeRequestDto)throws Exception{
//        likeService.insert(artistLikeRequestDto);
//        return success(null);
//    }
//
//
//
//    // feedPost 좋아요 취소 기능
//    @DeleteMapping("/feedPost")
//    public ResponseResult<?> delete(@RequestBody @Valid FanLikeRequestDto fanLikeRequestDto){
//        likeService.delete(fanLikeRequestDto);
//        return success(null);
//    }
//
//    // artistPost 좋아요 취소 기능
//    @DeleteMapping("/artistPost")
//    public ResponseResult<?> delete(@RequestBody @Valid ArtistLikeRequestDto artistLikeRequestDto){
//        likeService.delete(artistLikeRequestDto);
//        return success(null);
//    }

}
