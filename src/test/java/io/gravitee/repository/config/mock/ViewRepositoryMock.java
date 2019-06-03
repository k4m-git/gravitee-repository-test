/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.repository.config.mock;

import io.gravitee.repository.management.api.ViewRepository;
import io.gravitee.repository.management.model.View;

import java.util.Date;
import java.util.Set;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.collections.Sets.newSet;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class ViewRepositoryMock extends AbstractRepositoryMock<ViewRepository> {

    public ViewRepositoryMock() {
        super(ViewRepository.class);
    }

    @Override
    void prepare(ViewRepository viewRepository) throws Exception {
        final View newView = mock(View.class);
        when(newView.getName()).thenReturn("View name");
        when(newView.getEnvironment()).thenReturn("DEFAULT");
        when(newView.getDescription()).thenReturn("Description for the new view");
        when(newView.getCreatedAt()).thenReturn(new Date(1486771200000L));
        when(newView.getUpdatedAt()).thenReturn(new Date(1486771200000L));
        when(newView.isHidden()).thenReturn(true);
        when(newView.getOrder()).thenReturn(1);
        when(newView.isDefaultView()).thenReturn(true);
        when(newView.getPicture()).thenReturn("New picture");


        final View viewProducts = new View();
        viewProducts.setId("view");
        viewProducts.setEnvironment("DEFAULT");
        viewProducts.setName("Products");
        viewProducts.setCreatedAt(new Date(1000000000000L));
        viewProducts.setUpdatedAt(new Date(1111111111111L));
        viewProducts.setHidden(false);
        viewProducts.setOrder(1);
        viewProducts.setDefaultView(false);

        final View viewProductsUpdated = mock(View.class);
        when(viewProductsUpdated.getName()).thenReturn("New product");
        when(viewProductsUpdated.getEnvironment()).thenReturn("new_DEFAULT");
        when(viewProductsUpdated.getDescription()).thenReturn("New description");
        when(viewProductsUpdated.getCreatedAt()).thenReturn(new Date(1486771200000L));
        when(viewProductsUpdated.getUpdatedAt()).thenReturn(new Date(1486771200000L));
        when(viewProductsUpdated.isHidden()).thenReturn(true);
        when(viewProductsUpdated.getOrder()).thenReturn(10);
        when(viewProductsUpdated.isDefaultView()).thenReturn(true);
        when(viewProductsUpdated.getHighlightApi()).thenReturn("new Highlighted API");
        when(viewProductsUpdated.getPicture()).thenReturn("New picture");

        final Set<View> views = newSet(newView, viewProducts, mock(View.class));
        final Set<View> viewsAfterDelete = newSet(newView, viewProducts);
        final Set<View> viewsAfterAdd = newSet(newView, viewProducts, mock(View.class), mock(View.class));

        when(viewRepository.findAll()).thenReturn(views, viewsAfterAdd, views, viewsAfterDelete, views);
        when(viewRepository.findAllByEnvironment("DEFAULT")).thenReturn(views);

        when(viewRepository.create(any(View.class))).thenReturn(newView);

        when(viewRepository.findById("new-view")).thenReturn(of(newView));
        when(viewRepository.findById("unknown")).thenReturn(empty());
        when(viewRepository.findById("products")).thenReturn(of(viewProducts), of(viewProductsUpdated));

        when(viewRepository.update(argThat(o -> o == null || o.getId().equals("unknown")))).thenThrow(new IllegalStateException());
    }
}
