/*
 * Copyright 2000-2017 Vaadin Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.spring.tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.tutorial.model.ExampleDto;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route
public class MainView extends VerticalLayout implements LocaleChangeObserver {

	private Select<ExampleDto> customRendererSelect;

	private RouterLink link;

	public MainView(@Autowired Greeter greeter) {
		H1 heading = new H1("Vaadin + Spring examples");

		Span greeting = new Span(greeter.sayHello());
		Style grretingStyle = greeting.getElement().getStyle();
		grretingStyle.set("display", "block");
		grretingStyle.set("margin-bottom", "10px");

		Button button = new Button("Switch language to Chinese",
				event -> getUI().get().setLocale(Locale.CHINESE));

		link = new RouterLink(
				getTranslation("root.navigate_to_component"),
				ViewComponent.class);

		Style linkStyle = link.getElement().getStyle();
		linkStyle.set("display", "block");
		linkStyle.set("margin-bottom", "10px");

		customRendererSelect = new Select<>();
		customRendererSelect.setLabel("Test");
		customRendererSelect.setEmptySelectionCaption("None");
		customRendererSelect.setRenderer(createImageRenderer());
		customRendererSelect.setEmptySelectionAllowed(true);
		customRendererSelect.setItems(createExampleData());

		add(heading, greeting, button, link, customRendererSelect, new Button("Foobar"));
	}

	@Override
	public void localeChange(LocaleChangeEvent event) {
		link.setText(
				getTranslation("root.navigate_to_component"));
	}

	private List<ExampleDto> createExampleData() {
		List<ExampleDto> data = new ArrayList<>();

		data.add(new ExampleDto("https://picsum.photos/seed/vaadin/200/80"));
		data.add(new ExampleDto("https://picsum.photos/seed/flow/200/80"));
		data.add(new ExampleDto("https://picsum.photos/seed/hilla/200/80"));
		data.add(new ExampleDto("https://picsum.photos/seed/spring/200/80"));

		return data;
	}

	private ComponentRenderer<? extends Component, ExampleDto> createImageRenderer() {
		return new ComponentRenderer<>(dto -> {
			FlexLayout wrapper = new FlexLayout();
			wrapper.setAlignItems(FlexLayout.Alignment.CENTER);

			Image image = new Image();
			image.setSrc(dto.getUrl());
			image.setAlt("Image");
			image.setWidth(250, Unit.PIXELS);

			wrapper.add(image);

			return wrapper;
		});
	}
}
