package com.delbot.danam.domain.board.repository;

import static com.delbot.danam.domain.board.entity.QBoardComment.boardComment;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.delbot.danam.domain.board.entity.BoardComment;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardCommentCustomRepositoryImpl implements BoardCommentCustomRepository{
  //
  private final JPAQueryFactory queryFactory;

  @Override
  public List<BoardComment> findByBoardId(Long id) {
    //
    return queryFactory.selectFrom(boardComment)
      .leftJoin(boardComment.parent).fetchJoin()
      .where(boardComment.board.id.eq(id))
      .orderBy(boardComment.parent.id.asc().nullsFirst(),
        boardComment.createdTime.asc())
      .fetch();
  }

  @Override
  public Optional<BoardComment> findBoardCommentByIdWithParent(Long id) {
    //
    BoardComment selectedComment = queryFactory.select(boardComment)
      .from(boardComment)
      .leftJoin(boardComment.parent).fetchJoin()
      .where(boardComment.id.eq(id))
      .fetchOne();

    return Optional.ofNullable(selectedComment);
  }
  
}
