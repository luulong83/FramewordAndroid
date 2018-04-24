package example.com.githubissues.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import example.com.githubissues.entities.ApiResponse;
import example.com.githubissues.repositories.IssueRepository;

/**
 * Created by James on 5/21/2017.
 */

public class ListIssuesViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> apiResponse;
    private IssueRepository issueRepository;

    @Inject
    public ListIssuesViewModel(IssueRepository issueRepositoryry) {
        apiResponse = new MediatorLiveData<>();
        issueRepository = issueRepositoryry;
    }

    @NonNull
    public LiveData<ApiResponse> getApiResponse() {
        return apiResponse;
    }

    public void loadIssues(@NonNull String user, String repo) {
        LiveData<ApiResponse> issuesSource = issueRepository.getIssues(user, repo);
        apiResponse.addSource(
                issuesSource,
                apiResponse -> {
                    if (this.apiResponse.hasActiveObservers()) {
                        this.apiResponse.removeSource(issuesSource);
                    }
                    this.apiResponse.setValue(apiResponse);
                }
        );
    }

}