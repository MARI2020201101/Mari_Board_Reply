package org.mariworld.boardreply.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    void search1();
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
