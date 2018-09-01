/*
 * Copyright 2016 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.jenkinseditor.outline;


import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;

import de.jcup.egradle.core.model.Item;
import de.jcup.egradle.core.model.ItemType;
import de.jcup.egradle.core.model.Modifier;
import de.jcup.egradle.eclipse.util.ColorManager;
import de.jcup.egradle.eclipse.util.EclipseDevelopmentSettings;
import de.jcup.egradle.eclipse.util.EclipseUtil;
import de.jcup.jenkins.OutlinePipelineDSL;
import de.jcup.jenkinseditor.JenkinsEditorActivator;
import de.jcup.jenkinseditor.preferences.JenkinsEditorColorConstants;

public class JenkinsEditorOutlineLabelProvider extends BaseLabelProvider
		implements IStyledLabelProvider, IColorProvider {

	
	
	/* groovy */
	private static final String ICON_CLASS = "class_obj.png";
	private static final String ICON_INTERFACE = "int_obj.png";
	private static final String ICON_ENUM = "enum_obj.png";
	
	private static final String ICON_CLOSURE = "closure-parts.png";// "all_sc_obj.png";
	private static final String ICON_ASSIGNMENT = "assignment.png";
	private static final String ICON_PACKAGE = "package_obj.png";

	private static final String ICON_PRIVATE_CO_PNG = "private_co.png";
	private static final String ICON_PROTECTED_CO_PNG = "protected_co.png";
//	private static final String ICON_DEFAULT_CO_PNG = "default_co.png";
	private static final String ICON_PUBLIC_CO_PNG = "public_co.png";
	
	/* fields */
	private static final String ICON_PRIVATE_FIELD_PNG = "field_private_obj.png";
	private static final String ICON_PROTECTED_FIELD_PNG = "field_protected_obj.png";
	// private static final String ICON_DEFAULT_FIELD_PNG = "field_default_obj.png";
	private static final String ICON_PUBLIC_FIELD_PNG = "field_public_obj.png";
	
	/* other */
	private static final String ICON_UNKNOWN = "unknown_obj.png";

	
	
	private Styler outlineItemConfigurationStyler = new Styler() {

		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = getColorManager().getColor(JenkinsEditorColorConstants.DARK_GRAY);
		}
	};

	private Styler outlineItemInfoStyler = new Styler() {

		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = getColorManager().getColor(JenkinsEditorColorConstants.BRIGHT_BLUE);
		}
	};

	private Styler outlineItemTargetStyler = new Styler() {

		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = getColorManager().getColor(JenkinsEditorColorConstants.DARK_GRAY);
		}
	};

	private Styler outlineItemTypeStyler = new Styler() {

		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = getColorManager().getColor(JenkinsEditorColorConstants.OUTLINE_ITEM__TYPE);
		}
	};

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		return null;// getColorManager().getColor(JenkinsEditorColorConstants.BLACK);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Item) {
			Item item = (Item) element;
			ItemType type = item.getLastChainedItemType();
			
			if (type==null){
				type = item.getItemType();
			}
			if (type==null){
				return null;
			}
			
			Modifier modifier = item.getModifier();
			String path = null;
			switch (type) {
			case ASSIGNMENT:
				path =  ICON_ASSIGNMENT;
				return getOutlineImage(path);
			case VARIABLE:
				switch (modifier) {
				case PRIVATE:
					path = ICON_PRIVATE_FIELD_PNG;
					break;
				case PROTECTED:
					path = ICON_PROTECTED_FIELD_PNG;
					break;
				case PUBLIC:
					path = ICON_PUBLIC_FIELD_PNG;
					break;
				case DEFAULT:
					path = ICON_PUBLIC_FIELD_PNG;// default in groovy IS PUBLIC!
												// ICON_DEFAULT_CO_PNG;
					break;
				default:
					return null;
				}
				return getOutlineImage(path);
			case CONSTRUCTOR:
			case METHOD:
				switch (modifier) {
				case PRIVATE:
					path = ICON_PRIVATE_CO_PNG;
					break;
				case PROTECTED:
					path = ICON_PROTECTED_CO_PNG;
					break;
				case PUBLIC:
					path = ICON_PUBLIC_CO_PNG;
					break;
				case DEFAULT:
					path = ICON_PUBLIC_CO_PNG;// default in groovy IS PUBLIC!
												// ICON_DEFAULT_FIELD_PNG;
					break;
				default:
					return null;
				}
				return getOutlineImage(path);
			case CLOSURE:
				String name = item.getName();
				if (name==null){
					return getOutlineImage(ICON_CLOSURE);
				}
				name = name.toUpperCase().split(" ")[0];
				for (OutlinePipelineDSL dsl: OutlinePipelineDSL.values()){
					if (dsl.name().equals(name)){
						return getOutlineImage(dsl);
					}
				}
				
				return getOutlineImage(ICON_CLOSURE);
			case CLASS:
				return getOutlineImage(ICON_CLASS);
			case INTERFACE:
				return getOutlineImage(ICON_INTERFACE);
			case ENUM:
				return getOutlineImage(ICON_ENUM);
			case ENUM_CONSTANT:
				return getOutlineImage(ICON_PUBLIC_FIELD_PNG);
			case PACKAGE:
				return getOutlineImage(ICON_PACKAGE);
			default:
				return getOutlineImage(ICON_UNKNOWN);
			}
		}
		return null;
	}

	@Override
	public StyledString getStyledText(Object element) {
		StyledString styled = new StyledString();
		if (element == null) {
			styled.append("null");
		}
		if (element instanceof Item) {
			Item item = (Item) element;

			String configuration = item.getConfiguration();
			if (configuration != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(configuration);
				sb.append(" ");
				styled.append(new StyledString(sb.toString(), outlineItemConfigurationStyler));
			}

			String name = item.getName();
			if (name != null) {
				styled.append(name);
				handleParameters(styled, item);
			}

			String type = item.getType();
			if (type != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(" :");
				sb.append(type);
				StyledString typeString = new StyledString(sb.toString(), outlineItemTypeStyler);
				styled.append(typeString);
			}
			String target = item.getTarget();
			if (target != null) {
				StyledString targetString = new StyledString(":" + target, outlineItemTargetStyler);
				styled.append(targetString);
			}

			String info = item.getInfo();
			if (info != null) {
				StringBuilder sb = new StringBuilder();
				sb.append("  [");
				sb.append(info);
				sb.append(']');

				StyledString infoString = new StyledString(sb.toString(), outlineItemInfoStyler);
				styled.append(infoString);
			}
			if (EclipseDevelopmentSettings.DEBUG_ADD_SPECIAL_TEXTS) {
				StringBuilder sb = new StringBuilder();
				sb.append(" --[");
				sb.append(item.getItemType());
				String[] parameters = item.getParameters();
				if (parameters!=null && parameters.length>0){
					sb.append(", params={");
					boolean first=true;
					for (String param: parameters){
						if (!first){
							sb.append(", ");
						}
						first=false;
						sb.append(param);
					}
					sb.append('}');
				}
				sb.append(']');
				StyledString debugString = new StyledString(sb.toString(), outlineItemConfigurationStyler);
				styled.append(debugString);
			}
		} else {
			return styled.append(element.toString());
		}

		return styled;
	}

	private void handleParameters(StyledString styled, Item item) {
		/* show add parameters if existing */
		boolean paramsMustBeShown=false;
		
		ItemType itemType = item.getItemType();
		paramsMustBeShown=paramsMustBeShown || ItemType.METHOD.equals(itemType);
		paramsMustBeShown=paramsMustBeShown || ItemType.CONSTRUCTOR.equals(itemType);
		
		if (!paramsMustBeShown){
			return;
		}
		String[] parameters = item.getParameters();
		if (parameters == null) {
			return;
		}
		int length = parameters.length;
		styled.append("(");
		if (parameters != null && length > 0) {
			int last = length - 1;
			for (int i = 0; i < length; i++) {
				String parameter = parameters[i];
				if (parameter == null) {
					continue;
				}
				styled.append(parameter);
				if (i != last) {
					styled.append(',');
				}
			}
		}
		styled.append(")");
	}

	public ColorManager getColorManager() {
		JenkinsEditorActivator editorActivator =JenkinsEditorActivator.getDefault();
		if (editorActivator == null) {
			return ColorManager.getStandalone();
		}
		return editorActivator.getColorManager();
	}

	public Image getOutlineImage(OutlinePipelineDSL dsl){
		return EclipseUtil.getImage(dsl.getRelativePathInsidePlugin(), JenkinsEditorActivator.PLUGIN_ID);
	}
	
	
	public Image getOutlineImage(String name) {
		/* important: we use /icons/jenkinseditor as start so no conflicts with egradle editor parts
		 * (The eclipse util implementation does cache images, so same pathes will make conflicts)
		 */
		return EclipseUtil.getImage("/icons/jenkinseditor/outline/" + name, JenkinsEditorActivator.PLUGIN_ID);
	}

}
