/*
 * Copyright (c) 2012, B3log Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.symphony.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.b3log.latke.Keys;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.repository.SortDirection;
import org.b3log.latke.service.ServiceException;
import org.b3log.symphony.model.Article;
import org.b3log.symphony.repository.ArticleRepository;
import org.json.JSONObject;

/**
 * Article query service.
 *
 * @author <a href="mailto:DL88250@gmail.com">Liang Ding</a>
 * @version 1.0.0.0, Oct 2, 2012
 * @since 0.2.0
 */
public final class ArticleQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleQueryService.class.getName());
    /**
     * Singleton.
     */
    private static final ArticleQueryService SINGLETON = new ArticleQueryService();
    /**
     * Article repository.
     */
    private ArticleRepository articleRepository = ArticleRepository.getInstance();

    /**
     * Gets the recent articles.
     * 
     * @param pageSize the specified page size
     * @return recent articles, returns an empty list if not found
     * @throws ServiceException service exception
     */
    public List<JSONObject> getRecentArticles(final int pageSize) throws ServiceException {
        final Query query = new Query().addSort(Article.ARTICLE_CREATE_TIME, SortDirection.DESCENDING)
                .setPageCount(1).setPageSize(pageSize);
        try {
            final JSONObject result = articleRepository.get(query);
            return org.b3log.latke.util.CollectionUtils.jsonArrayToList(result.optJSONArray(Keys.RESULTS));
        } catch (final RepositoryException e) {
            LOGGER.log(Level.SEVERE, "Gets recent articles failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets the {@link ArticleQueryService} singleton.
     *
     * @return the singleton
     */
    public static ArticleQueryService getInstance() {
        return SINGLETON;
    }

    /**
     * Private constructor.
     */
    private ArticleQueryService() {
    }
}