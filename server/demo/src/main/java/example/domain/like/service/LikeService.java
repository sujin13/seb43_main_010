package example.domain.like.service;

import example.domain.artist.entity.Artist;
import example.domain.artist.repository.ArtistRepository;
import example.domain.artistPost.entity.ArtistPost;
import example.domain.comment.entity.Comment;
import example.domain.like.dto.LikeRequestDto;
import example.domain.like.entity.Like;
import example.domain.like.repository.LikeRepository;
import example.global.exception.DuplicateResourceException;
import example.global.exception.NotFoundException;
import example.domain.fans.entity.Fans;
import example.domain.fans.repository.FansRepository;
import example.domain.feedPost.entity.FeedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import example.domain.feedPost.repository.feedPostRepository;
import org.springframework.transaction.annotation.Transactional;
import example.domain.artistPost.repository.artistPostRepository;
import example.domain.comment.repository.CommentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final FansRepository fansRepository;
    private final ArtistRepository artistRepository;
    private final feedPostRepository feedPostRepository;
    private final artistPostRepository artistPostRepository;
    private final CommentRepository commentRepository;

    // feedPost fan이 좋아요

    @Transactional
    public void insertFeedFanLike(Integer groupId, LikeRequestDto likeRequestDto) throws Exception {
        FeedPost feedPost = feedPostRepository.findById(likeRequestDto.getFeedPostId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물을 찾을 수 없습니다."));

        // 특정 그룹에 속하는지 확인
        if (!feedPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물은 그룹 ID " + groupId + "에 속하지 않습니다.");
        }

        // 팬이 이미 좋아요를 누른 경우는 예외를 발생시키지 않고, 계속 진행
        Optional<Like> existingFanLike = likeRepository.findByFansAndFeedPost(feedPost.getFan(), feedPost);
        if (!existingFanLike.isPresent()) {
            Like fanLike = Like.builder()
                    .feedPost(feedPost)
                    .fans(feedPost.getFan())
                    .build();

            likeRepository.save(fanLike);
            feedPostRepository.addLikeCount(feedPost);
        } else if (!existingFanLike.get().getArtist().equals(feedPost.getArtist())) {
            // 이미 좋아요를 누른 아티스트가 다른 아티스트인 경우에만 예외를 발생시킴
            throw new DuplicateResourceException("ID가 " + feedPost.getFan().getFanId() + "인 팬은 이미 좋아요를 누른 피드 게시물입니다.");
        }
    }

    // feedPost artist가 좋아요
    @Transactional
    public void insertFeedArtistLike(Integer groupId, LikeRequestDto likeRequestDto) throws Exception {
        FeedPost feedPost = feedPostRepository.findById(likeRequestDto.getFeedPostId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물을 찾을 수 없습니다."));

        // 특정 그룹에 속하는지 확인
        if (!feedPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물은 그룹 ID " + groupId + "에 속하지 않습니다.");
        }

        // 아티스트가 이미 좋아요를 누른 경우는 예외를 발생시키지 않고, 계속 진행
        Optional<Like> existingArtistLike = likeRepository.findByArtistAndFeedPost(feedPost.getArtist(), feedPost);
        if (!existingArtistLike.isPresent()) {
            Like artistLike = Like.builder()
                    .feedPost(feedPost)
                    .artist(feedPost.getArtist())
                    .build();

            likeRepository.save(artistLike);
            feedPostRepository.addLikeCount(feedPost);
        } else if (!existingArtistLike.get().getFans().equals(feedPost.getFan())) {
            // 이미 좋아요를 누른 팬이 다른 팬인 경우에만 예외를 발생시킴
            throw new DuplicateResourceException("ID가 " + feedPost.getArtist().getArtistId() + "인 아티스트는 이미 좋아요를 누른 피드 게시물입니다.");
        }
    }


    // artistPost fan이 좋아요


    @Transactional
    public void insertArtistFanLike(Integer groupId, LikeRequestDto likeRequestDto) throws Exception {
        ArtistPost artistPost = artistPostRepository.findById(likeRequestDto.getArtistPostId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getArtistPostId() + "인 피드 게시물을 찾을 수 없습니다."));

        // 특정 그룹에 속하는지 확인
        if (!artistPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getArtistPostId() + "인 피드 게시물은 그룹 ID " + groupId + "에 속하지 않습니다.");
        }

        // 팬이 이미 좋아요를 누른 경우는 예외를 발생시키지 않고, 계속 진행
        Optional<Like> existingFanLike = likeRepository.findByFansAndArtistPost(artistPost.getFan(), artistPost);
        if (!existingFanLike.isPresent()) {
            Like fanLike = Like.builder()
                    .artistPost(artistPost)
                    .fans(artistPost.getFan())
                    .build();

            likeRepository.save(fanLike);
            artistPostRepository.addLikeCount(artistPost);
        } else if (!existingFanLike.get().getArtist().equals(artistPost.getArtist())) {
            // 이미 좋아요를 누른 아티스트가 다른 아티스트인 경우에만 예외를 발생시킴
            throw new DuplicateResourceException("ID가 " + artistPost.getFan().getFanId() + "인 팬은 이미 좋아요를 누른 피드 게시물입니다.");
        }
    }




    // artistPost artist가 좋아요
    @Transactional
    public void insertArtistArtistLike(Integer groupId, LikeRequestDto likeRequestDto) throws Exception {
        ArtistPost artistPost = artistPostRepository.findById(likeRequestDto.getArtistPostId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getArtistPostId() + "인 피드 게시물을 찾을 수 없습니다."));

        // 특정 그룹에 속하는지 확인
        if (!artistPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getArtistPostId() + "인 피드 게시물은 그룹 ID " + groupId + "에 속하지 않습니다.");
        }

        // 아티스트가 이미 좋아요를 누른 경우는 예외를 발생시키지 않고, 계속 진행
        Optional<Like> existingArtistLike = likeRepository.findByArtistAndArtistPost(artistPost.getArtist(), artistPost);
        if (!existingArtistLike.isPresent()) {
            Like artistLike = Like.builder()
                    .artistPost(artistPost)
                    .artist(artistPost.getArtist())
                    .build();

            likeRepository.save(artistLike);
            artistPostRepository.addLikeCount(artistPost);
        } else if (!existingArtistLike.get().getFans().equals(artistPost.getFan())) {
            // 이미 좋아요를 누른 팬이 다른 팬인 경우에만 예외를 발생시킴
            throw new DuplicateResourceException("ID가 " + artistPost.getArtist().getArtistId() + "인 아티스트는 이미 좋아요를 누른 피드 게시물입니다.");
        }
    }

//    @Transactional
//    public void deleteFeedFanLike(Integer groupId, LikeRequestDto likeRequestDto) {
//        Fans fans = fansRepository.findById(likeRequestDto.getFanId())
//                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getFanId() + "인 팬을 찾을 수 없습니다."));
//
//        FeedPost feedPost = feedPostRepository.findById(likeRequestDto.getFeedPostId())
//                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물을 찾을 수 없습니다."));
//
//        // 특정 그룹에 속하는지 확인
//        if (!feedPost.getGroup().getId().equals(groupId)) {
//            throw new IllegalArgumentException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물은 그룹 ID " + groupId + "에 속하지 않습니다.");
//        }
//
//        if (feedPost.getArtist().getArtistId().equals(fans.getFanId())) {
//            throw new IllegalArgumentException("아티스트 본인은 팬의 좋아요를 취소할 수 없습니다.");
//        }
//
//        if (likeRepository.findByFansAndFeedPost(fans, feedPost).isPresent()) {
//            Like like = likeRepository.findByFansAndFeedPost(fans, feedPost)
//                    .orElseThrow(() -> new NotFoundException("해당 팬과 피드 게시물에 대한 좋아요를 찾을 수 없습니다."));
//
//            likeRepository.delete(like);
//            feedPostRepository.subLikeCount(feedPost);
//        } else {
//            throw new NotFoundException("해당 팬과 피드 게시물에 대한 좋아요를 찾을 수 없습니다.");
//        }
//    }
//
//    @Transactional
//    public void deleteFeedArtistLike(Integer groupId, LikeRequestDto likeRequestDto) {
//        Artist artist = artistRepository.findById(likeRequestDto.getArtistId())
//                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getArtistId() + "인 아티스트를 찾을 수 없습니다."));
//
//        FeedPost feedPost = feedPostRepository.findById(likeRequestDto.getFeedPostId())
//                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물을 찾을 수 없습니다."));
//
//        // 특정 그룹에 속하는지 확인
//        if (!feedPost.getGroup().getId().equals(groupId)) {
//            throw new IllegalArgumentException("ID가 " + likeRequestDto.getFeedPostId() + "인 피드 게시물은 그룹 ID " + groupId + "에 속하지 않습니다.");
//        }
//
//        if (feedPost.getFan().getFanId().equals(artist.getArtistId())) {
//            throw new IllegalArgumentException("팬 본인은 아티스트의 좋아요를 취소할 수 없습니다.");
//        }
//
//        if (likeRepository.findByArtistAndFeedPost(artist, feedPost).isPresent()) {
//            Like like = likeRepository.findByArtistAndFeedPost(artist, feedPost)
//                    .orElseThrow(() -> new NotFoundException("해당 아티스트와 피드 게시물에 대한 좋아요를 찾을 수 없습니다."));
//
//            likeRepository.delete(like);
//            feedPostRepository.subLikeCount(feedPost);
//        } else {
//            throw new NotFoundException("해당 아티스트와 피드 게시물에 대한 좋아요를 찾을 수 없습니다.");
//        }
//    }


    // feedPost에서 fan이 좋아요 취소
    @Transactional
    public void deleteFeedFanLike(Integer groupId, LikeRequestDto likeRequestDto) {
        Fans fans = fansRepository.findById(likeRequestDto.getFanId())
                .orElseThrow(() -> new NotFoundException("Could not find fan with id: " + likeRequestDto.getFanId()));

        FeedPost feedPost = feedPostRepository.findById(likeRequestDto.getFeedPostId())
                .orElseThrow(() -> new NotFoundException("Could not find feedPost with id: " + likeRequestDto.getFeedPostId()));

        // 특정 그룹에 속하는지 확인
        if (!feedPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("The feedPost with id: " + likeRequestDto.getFeedPostId()
                    + " does not belong to group with id: " + groupId);
        }

//        if (likeRepository.findByFansAndFeedPost(feedPost.getFan(), feedPost).isPresent()) {
//            // 이미 좋아요를 누른 팬인 경우는 예외를 발생시키지 않고, 계속 진행
//            if (!feedPost.getArtist().getArtistId().equals(feedPost.getFan().getFanId())) {
//                // todo 409 에러로 변경
//                throw new DuplicateResourceException("아티스트 ID " + feedPost.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 피드 게시물 ID: " + feedPost.getId());
//            } else {

                Like like = likeRepository.findByFansAndFeedPost(fans, feedPost)
                        .orElseThrow(() -> new NotFoundException("Could not find like"));

                likeRepository.delete(like);
                feedPostRepository.subLikeCount(feedPost);
            }



    // feedPost에서 artist가 좋아요 취소
    @Transactional
    public void deleteFeedArtistLike(Integer groupId, LikeRequestDto likeRequestDto) {
        Artist artist = artistRepository.findById(likeRequestDto.getArtistId())
                .orElseThrow(() -> new NotFoundException("Could not find artist with id: " + likeRequestDto.getArtistId()));

        FeedPost feedPost = feedPostRepository.findById(likeRequestDto.getFeedPostId())
                .orElseThrow(() -> new NotFoundException("Could not find feedPost with id: " + likeRequestDto.getFeedPostId()));

        // 특정 그룹에 속하는지 확인
        if (!feedPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("The feedPost with id: " + likeRequestDto.getFeedPostId()
                    + " does not belong to group with id: " + groupId);
        }

//        if (likeRepository.findByArtistAndFeedPost(feedPost.getArtist(), feedPost).isPresent()) {
//            // 이미 좋아요를 누른 팬인 경우는 예외를 발생시키지 않고, 계속 진행
//            if (!feedPost.getFan().getFanId().equals(feedPost.getArtist().getArtistId())) {
//                // todo 409 에러로 변경
//                throw new DuplicateResourceException("아티스트 ID " + feedPost.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 피드 게시물 ID: " + feedPost.getId());
//            } else {

                Like like = likeRepository.findByArtistAndFeedPost(artist, feedPost)
                        .orElseThrow(() -> new NotFoundException("Could not find like"));

                likeRepository.delete(like);
                feedPostRepository.subLikeCount(feedPost);
            }


    //    // artistPost에서 fan이 좋아요 취소
    @Transactional
    public void deleteArtistFanLike(Integer groupId, LikeRequestDto likeRequestDto) {
        Fans fans = fansRepository.findById(likeRequestDto.getFanId())
                .orElseThrow(() -> new NotFoundException("Could not find fan with id: " + likeRequestDto.getFanId()));

        ArtistPost artistPost = artistPostRepository.findById(likeRequestDto.getArtistPostId())
                .orElseThrow(() -> new NotFoundException("Could not find feedPost with id: " + likeRequestDto.getArtistPostId()));

        // 특정 그룹에 속하는지 확인
        if (!artistPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("The feedPost with id: " + likeRequestDto.getArtistPostId()
                    + " does not belong to group with id: " + groupId);
        }

//        if (likeRepository.findByFansAndFeedPost(feedPost.getFan(), feedPost).isPresent()) {
//            // 이미 좋아요를 누른 팬인 경우는 예외를 발생시키지 않고, 계속 진행
//            if (!feedPost.getArtist().getArtistId().equals(feedPost.getFan().getFanId())) {
//                // todo 409 에러로 변경
//                throw new DuplicateResourceException("아티스트 ID " + feedPost.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 피드 게시물 ID: " + feedPost.getId());
//            } else {

        Like like = likeRepository.findByFansAndArtistPost(fans, artistPost)
                .orElseThrow(() -> new NotFoundException("Could not find like"));

        likeRepository.delete(like);
        artistPostRepository.subLikeCount(artistPost);
    }


//    // artistPost에서 fan이 좋아요 취소
//    @Transactional
//    public void deleteArtistFanLike(Integer groupId, LikeRequestDto likeRequestDto) {
//        Fans fans = fansRepository.findById(likeRequestDto.getFanId())
//                .orElseThrow(() -> new NotFoundException("Could not find fan with id: " + likeRequestDto.getFanId()));
//
//        ArtistPost artistPost = artistPostRepository.findById(likeRequestDto.getArtistPostId())
//                .orElseThrow(() -> new NotFoundException("Could not find artistPost with id: " + likeRequestDto.getArtistPostId()));
//
//        if (!artistPost.getGroup().getId().equals(groupId)) {
//            throw new IllegalArgumentException("The feedPost with id: " + likeRequestDto.getArtistPostId()
//                    + " does not belong to group with id: " + groupId);
//        }
//
//        // 특정 그룹에 속하는지 확인
//        if (likeRepository.findByFansAndArtistPost(artistPost.getFan(), artistPost).isPresent()) {
//            // 이미 좋아요를 누른 팬인 경우는 예외를 발생시키지 않고, 계속 진행
//            if (!artistPost.getArtist().getArtistId().equals(artistPost.getFan().getFanId())) {
//                // todo 409 에러로 변경
//                throw new DuplicateResourceException("아티스트 ID " + artistPost.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 피드 게시물 ID: " + artistPost.getId());
//            } else {
//
//            Like like = likeRepository.findByFansAndArtistPost(fans, artistPost)
//                    .orElseThrow(() -> new NotFoundException("Could not find like"));
//
//            likeRepository.delete(like);
//            artistPostRepository.subLikeCount(artistPost);
//            }
//        }
//    }
//

        // artistPost에서 artist가 좋아요 취소
    @Transactional
    public void deleteArtistArtistLike(Integer groupId, LikeRequestDto likeRequestDto) {
        Artist artist = artistRepository.findById(likeRequestDto.getArtistId())
                .orElseThrow(() -> new NotFoundException("Could not find artist with id: " + likeRequestDto.getArtistId()));

        ArtistPost artistPost = artistPostRepository.findById(likeRequestDto.getArtistPostId())
                .orElseThrow(() -> new NotFoundException("Could not find feedPost with id: " + likeRequestDto.getArtistPostId()));

        // 특정 그룹에 속하는지 확인
        if (!artistPost.getGroup().getId().equals(groupId)) {
            throw new IllegalArgumentException("The feedPost with id: " + likeRequestDto.getArtistPostId()
                    + " does not belong to group with id: " + groupId);
        }

//        if (likeRepository.findByArtistAndFeedPost(feedPost.getArtist(), feedPost).isPresent()) {
//            // 이미 좋아요를 누른 팬인 경우는 예외를 발생시키지 않고, 계속 진행
//            if (!feedPost.getFan().getFanId().equals(feedPost.getArtist().getArtistId())) {
//                // todo 409 에러로 변경
//                throw new DuplicateResourceException("아티스트 ID " + feedPost.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 피드 게시물 ID: " + feedPost.getId());
//            } else {

        Like like = likeRepository.findByArtistAndArtistPost(artist, artistPost)
                .orElseThrow(() -> new NotFoundException("Could not find like"));

        likeRepository.delete(like);
        artistPostRepository.subLikeCount(artistPost);
    }



//
//
//
//    // artistPost에서 artist가 좋아요 취소
//    @Transactional
//    public void deleteArtistArtistLike(Integer groupId, LikeRequestDto likeRequestDto) {
//        Artist artist = artistRepository.findById(likeRequestDto.getArtistId())
//                .orElseThrow(() -> new NotFoundException("Could not find artist with id: " + likeRequestDto.getArtistId()));
//
//        ArtistPost artistPost = artistPostRepository.findById(likeRequestDto.getArtistPostId())
//                .orElseThrow(() -> new NotFoundException("Could not find artistPost with id: " + likeRequestDto.getArtistPostId()));
//
//        // 특정 그룹에 속하는지 확인
//        if (!artistPost.getGroup().getId().equals(groupId)) {
//            throw new IllegalArgumentException("The artistPost with id: " + likeRequestDto.getArtistPostId()
//                    + " does not belong to group with id: " + groupId);
//        }
//        if (likeRepository.findByArtistAndArtistPost(artistPost.getArtist(), artistPost).isPresent()) {
//            // 이미 좋아요를 누른 팬인 경우는 예외를 발생시키지 않고, 계속 진행
//            if (!artistPost.getFan().getFanId().equals(artistPost.getArtist().getArtistId())) {
//                // todo 409 에러로 변경
//                throw new DuplicateResourceException("아티스트 ID " + artistPost.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 피드 게시물 ID: " + artistPost.getId());
//            } else {
//
//
//                Like like = likeRepository.findByArtistAndArtistPost(artist, artistPost)
//                        .orElseThrow(() -> new NotFoundException("Could not find like"));
//
//                likeRepository.delete(like);
//                artistPostRepository.subLikeCount(artistPost);
//            }
//        }
//    }




    // feedPost에서 fan이 comment 좋아요
    @Transactional
    public void insertFeedCommentFanLike(LikeRequestDto likeRequestDto) throws Exception {
        Comment comment = commentRepository.findById(likeRequestDto.getCommentId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getCommentId() + "인 댓글을 찾을 수 없습니다."));

        // 특정 피드 게시물에 속하는지 확인
        if (!comment.getFeedPost().getId().equals(likeRequestDto.getFeedPostId())) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getCommentId() + "인 댓글은 피드 게시물 ID " + likeRequestDto.getFeedPostId() + "에 속하지 않습니다.");
        }


        // 이미 좋아요가 되어 있는 경우 예외 처리
        if (likeRepository.findByFansAndComment(comment.getFan(), comment).isPresent()) {
            throw new DuplicateResourceException("팬 id: " + comment.getFan().getFanId() + "로 이미 좋아요가 눌러진 댓글 Id: " + comment.getId());
        }

        // like 객체 생성
        Like like = Like.builder()
                .comment(comment)
                .fans(comment.getFan())
                .buildWithComment();

        likeRepository.save(like);
        commentRepository.addLikeCount(comment);

    }
//
//
//
    // feedPost에서 artist가 comment 좋아요
    @Transactional
    public void insertFeedCommentArtistLike(LikeRequestDto likeRequestDto) throws Exception {
        Comment comment = commentRepository.findById(likeRequestDto.getCommentId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getCommentId() + "인 댓글을 찾을 수 없습니다."));

        // 특정 피드 게시물에 속하는지 확인
        if (!comment.getFeedPost().getId().equals(likeRequestDto.getFeedPostId())) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getCommentId() + "인 댓글은 피드 게시물 ID " + likeRequestDto.getFeedPostId() + "에 속하지 않습니다.");
        }

        // 이미 좋아요가 되어 있는 경우 예외 처리
        if (likeRepository.findByArtistAndComment(comment.getArtist(), comment).isPresent()) {
            throw new DuplicateResourceException("아티스트 id: " + comment.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 댓글 Id: " + comment.getId());
        }

        // like 객체 생성
        Like like = Like.builder()
                .comment(comment)
                .artist(comment.getArtist())
                .buildWithComment();

        likeRepository.save(like);
        commentRepository.addLikeCount(comment);

    }

    // artistPost fan이 comment 좋아요
    @Transactional
    public void insertArtistCommentFanLike(LikeRequestDto likeRequestDto) throws Exception {
        Comment comment = commentRepository.findById(likeRequestDto.getCommentId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getCommentId() + "인 댓글을 찾을 수 없습니다."));

        // 특정 피드 게시물에 속하는지 확인
        if (!comment.getArtistPost().getId().equals(likeRequestDto.getArtistPostId())) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getCommentId() + "인 댓글은 피드 게시물 ID " + likeRequestDto.getArtistPostId() + "에 속하지 않습니다.");
        }


        // 이미 좋아요가 되어 있는 경우 예외 처리
        if (likeRepository.findByFansAndComment(comment.getFan(), comment).isPresent()) {
            throw new DuplicateResourceException("팬 id: " + comment.getFan().getFanId() + "로 이미 좋아요가 눌러진 댓글 Id: " + comment.getId());
        }

        // like 객체 생성
        Like like = Like.builder()
                .comment(comment)
                .fans(comment.getFan())
                .buildWithComment();

        likeRepository.save(like);
        commentRepository.addLikeCount(comment);
    }





    // artistPost artist가 comment 좋아요
    @Transactional
    public void insertArtistCommentArtistLike(LikeRequestDto likeRequestDto) throws Exception {
        Comment comment = commentRepository.findById(likeRequestDto.getCommentId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getCommentId() + "인 댓글을 찾을 수 없습니다."));

        // 특정 피드 게시물에 속하는지 확인
        if (!comment.getArtistPost().getId().equals(likeRequestDto.getArtistPostId())) {
            throw new IllegalArgumentException("ID가 " + likeRequestDto.getCommentId() + "인 댓글은 피드 게시물 ID " + likeRequestDto.getArtistPostId() + "에 속하지 않습니다.");
        }


        // 이미 좋아요가 되어 있는 경우 예외 처리
        if (likeRepository.findByArtistAndComment(comment.getArtist(), comment).isPresent()) {
            throw new DuplicateResourceException("팬 id: " + comment.getArtist().getArtistId() + "로 이미 좋아요가 눌러진 댓글 Id: " + comment.getId());
        }

        // like 객체 생성
        Like like = Like.builder()
                .comment(comment)
                .artist(comment.getArtist())
                .buildWithComment();

        likeRepository.save(like);
        commentRepository.addLikeCount(comment);
    }


    // feedPost comment에서 fan이 좋아요 취소
    @Transactional
    public void deleteFeedCommentFanLike(LikeRequestDto likeRequestDto) {
        Fans fans = fansRepository.findById(likeRequestDto.getFanId())
                .orElseThrow(() -> new NotFoundException("Could not find fan with id: " + likeRequestDto.getFanId()));

        Comment comment = commentRepository.findById(likeRequestDto.getCommentId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getCommentId() + "인 댓글을 찾을 수 없습니다."));


        Like like = likeRepository.findByFansAndComment(fans, comment)
                .orElseThrow(() -> new NotFoundException("Could not find like"));

        likeRepository.delete(like);
        commentRepository.subLikeCount(comment);
    }



    //     feedPost comment에서 artist가 좋아요 취소
    @Transactional
    public void deleteFeedCommentArtistLike(LikeRequestDto likeRequestDto) {
        Artist artist = artistRepository.findById(likeRequestDto.getArtistId())
                .orElseThrow(() -> new NotFoundException("Could not find artist with id: " + likeRequestDto.getArtistId()));

        FeedPost feedPost = feedPostRepository.findById(likeRequestDto.getFeedPostId())
                .orElseThrow(() -> new NotFoundException("Could not find feedPost with id: " + likeRequestDto.getFeedPostId()));

        Like like = likeRepository.findByArtistAndFeedPost(artist, feedPost)
                .orElseThrow(() -> new NotFoundException("Could not find like"));

        likeRepository.delete(like);
        feedPostRepository.subLikeCount(feedPost);
    }





    // artistPost fan이 comment 좋아요 취소
    @Transactional
    public void deleteArtistCommentFanLike(LikeRequestDto likeRequestDto) {
        Fans fans = fansRepository.findById(likeRequestDto.getFanId())
                .orElseThrow(() -> new NotFoundException("Could not find fan with id: " + likeRequestDto.getFanId()));

        Comment comment = commentRepository.findById(likeRequestDto.getCommentId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getCommentId() + "인 댓글을 찾을 수 없습니다."));

        Like like = likeRepository.findByFansAndComment(fans, comment)
                .orElseThrow(() -> new NotFoundException("Could not find like"));

        likeRepository.delete(like);
        commentRepository.subLikeCount(comment);
    }




    // artistPost artist가 comment 좋아요 취소

    @Transactional
    public void deleteArtistCommentArtistLike(LikeRequestDto likeRequestDto) {
        Artist artist = artistRepository.findById(likeRequestDto.getArtistId())
                .orElseThrow(() -> new NotFoundException("Could not find artist with id: " + likeRequestDto.getArtistId()));

        Comment comment = commentRepository.findById(likeRequestDto.getCommentId())
                .orElseThrow(() -> new NotFoundException("ID가 " + likeRequestDto.getCommentId() + "인 댓글을 찾을 수 없습니다."));


        Like like = likeRepository.findByArtistAndComment(artist, comment)
                .orElseThrow(() -> new NotFoundException("Could not find like"));

        likeRepository.delete(like);
        commentRepository.subLikeCount(comment);
    }

}
