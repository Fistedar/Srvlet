package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepository {
  ConcurrentHashMap<Long,Post> data = new ConcurrentHashMap<>();
  AtomicLong atomicId = new AtomicLong();
  public List<Post> all() {
    return (List<Post>) data.values();
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(data.get(id));
  }

  public Post save(Post post) {
    if(post.getId()==0){
      long id = atomicId.getAndIncrement();
      post.setId(id);
      data.put(id,post);
    }else if (post.getId()!=0 && data.containsKey(post.getId())){
      data.replace(post.getId(),post);
    } else {
      throw new NotFoundException("Нельзя добавлять эллемент с id < 0 и присваивать собственное значение для нового поста");
    }
    return post;
  }

  public void removeById(long id) {
      data.remove(id);
  }
}
