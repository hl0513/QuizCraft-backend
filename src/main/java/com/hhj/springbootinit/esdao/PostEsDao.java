package com.hhj.springbootinit.esdao;

import com.hhj.springbootinit.model.dto.post.PostEsDTO;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author <a href="https://github.com/hl0513">程序员泥嚎鸭</a>
 * @from <a href="https://www.yuque.com/dashboard/books">泥嚎鸭的语雀笔记</a>
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);
}