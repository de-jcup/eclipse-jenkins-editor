/*
 * Copyright 2017 Albert Tregnaghi
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
package de.jcup.jenkinseditor.codeassist;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.jcup.eclipse.commons.codeassist.AbstractWordCodeCompletition;
import de.jcup.eclipse.commons.codeassist.ProposalProvider;
import de.jcup.eclipse.commons.ui.EclipseUtil;
import de.jcup.egradle.core.model.BuildContext;
import de.jcup.egradle.core.model.Item;
import de.jcup.egradle.core.model.Model;
import de.jcup.egradle.core.model.ModelBuilder.ModelBuilderException;
import de.jcup.jenkins.JenkinsModelBuilder;
import de.jcup.jenkinseditor.JenkinsEditorActivator;
import de.jcup.jenkinseditor.codeassist.JenkinsDSLModel.Node;
import de.jcup.jenkinseditor.document.JenkinsDefaultClosureKeyWords;

public class JenkinsWordCompletion extends AbstractWordCodeCompletition {

    private JenkinsDSLModel model;

    public JenkinsWordCompletion() {
        model = new JenkinsDSLModel();
    }

    @Override
    public Set<ProposalProvider> calculate(String source, int offset) {
        Set<ProposalProvider> unfiltered = unfilteredCalculation(source, offset);

        String wanted = getTextbefore(source, offset);
        if (wanted == null || wanted.isEmpty()) {
            return unfiltered;
        }
        /* user has typed something, so filter unnecessary stuff... */
        wanted = wanted.toLowerCase();
        Set<ProposalProvider> filtered = new LinkedHashSet<ProposalProvider>();
        for (ProposalProvider provider : unfiltered) {
            String label = provider.getLabel();
            if (label == null) {
                continue;
            }

            label = label.toLowerCase();
            if (label.startsWith(wanted)) {
                filtered.add(provider);
            }
        }
        return filtered;

    }

    private Set<ProposalProvider> unfilteredCalculation(String source, int offset) {
        if (source.trim().length() == 0) {
            return Collections.singleton(new JenkinsDSLTypeProposalProvider(JenkinsDefaultClosureKeyWords.PIPELINE));
        }
        String parentAsText = "";

        JenkinsModelBuilder builder = new JenkinsModelBuilder(new ByteArrayInputStream(source.getBytes()));
        BuildContext builderContext = new BuildContext();
        Item item = null;
        try {
            Model model = builder.build(builderContext);
            item = model.getParentItemOf(offset);

        } catch (ModelBuilderException e) {
            EclipseUtil.logError("Cannot build proposal model", e, JenkinsEditorActivator.getDefault());
            return Collections.emptySet();
        }
        if (item.isRoot()) {
            /* no parent-> CHILD calculation not necessary */
            Set<ProposalProvider> set = Collections.singleton(new JenkinsDSLTypeProposalProvider(JenkinsDefaultClosureKeyWords.PIPELINE));
            return set;
        }
        parentAsText = item.getIdentifier();

        List<Node> foundParents = model.findByKeyword(parentAsText);
        if (foundParents.isEmpty()) {
            return Collections.emptySet();
        }
        return fetchChildrenProposalsForParents(source, offset, foundParents);
    }

    protected Set<ProposalProvider> fetchChildrenProposalsForParents(String source, int offset, List<Node> foundParents) {
        Set<ProposalProvider> set = new TreeSet<>();
        String alreadyTyped = getTextbefore(source, offset);

        List<Node> result = model.findByKeywordStartingWith(alreadyTyped);
        if (!result.isEmpty()) {
            for (Node node : result) {
                for (Node parent : node.parents) {
                    if (foundParents.contains(parent)) {
                        /* accepted by at least one parent */
                        set.add(new JenkinsDSLTypeProposalProvider(node.keyword));
                        break;
                    }
                }
            }
        }
        return set;
    }

    @Override
    public void reset() {
        /* no dynamic parts - so ignore */
    }

}
