
package com.hogwartsartifactsonline.wizard;

import com.hogwartsartifactsonline.artifact.ArtifactRepository;
import com.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class WizardService {

    private final WizardRepository wizardRepository;

    private final ArtifactRepository artifactRepository;


    public WizardService(WizardRepository wizardRepository, ArtifactRepository artifactRepository) {
        this.wizardRepository = wizardRepository;
        this.artifactRepository = artifactRepository;
    }

    public List<Wizard> findAll() {
        return this.wizardRepository.findAll();
    }

    public Wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("Wizard", wizardId));
    }

    public Wizard save(Wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    // We are not updating a wizard's artifacts through this method, we only update their name.
    public Wizard update(Integer wizardId, Wizard update) {
        return this.wizardRepository.findById(wizardId)
                .map(oldWizard -> {
                    oldWizard.setName(update.getName());
                    return this.wizardRepository.save(oldWizard);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Wizard", wizardId));
    }

    public void delete(Integer wizardId) {
        Wizard wizardToBeDeleted = this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("Wizard", wizardId));

        // Before deletion, we will unassign this wizard's owned artifacts.
        wizardToBeDeleted.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }

   /* public void assignArtifact(Integer wizardId, String artifactId){
        // Find this artifact by Id from DB.
        Artifact artifactToBeAssigned = this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException( artifactId));

        // Find this wizard by Id from DB.
        Wizard wizard = this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new WizardNotFoundException( wizardId));

        // Artifact assignment
        // We need to see if the artifact is already owned by some wizard.
        if (artifactToBeAssigned.getOwner() != null) {
            artifactToBeAssigned.getOwner().removeArtifact(artifactToBeAssigned);
        }
        wizard.addArtifact(artifactToBeAssigned);
    }
*/
}