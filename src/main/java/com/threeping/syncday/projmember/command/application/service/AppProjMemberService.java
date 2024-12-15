package com.threeping.syncday.projmember.command.application.service;
import com.threeping.syncday.projmember.command.aggregate.entity.BookmarkStatus;
import com.threeping.syncday.projmember.command.aggregate.entity.ProjMember;
import com.threeping.syncday.projmember.command.aggregate.entity.UpdateProjectMemberReq;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjRequest;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateProjResponse;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateWorkspaceRequest;
import com.threeping.syncday.projmember.command.aggregate.vo.UpdateWorkspaceResponse;
import com.threeping.syncday.workspace.command.aggregate.dto.WorkspaceDTO;


public interface AppProjMemberService {

    UpdateProjResponse addProj(UpdateProjRequest req);

    UpdateProjResponse updateProj(UpdateProjRequest req);

    WorkspaceDTO updateWorkspace(UpdateWorkspaceRequest updateWorkspaceRequest);

    Boolean addProjOwner(Long projId, Long userId);

    Boolean addProjBookmark(Long userId, Long projId);

    BookmarkStatus updateProjBookmark(Long projmemberId);

    Boolean removeProjBookmark(Long userId, Long projId);

    ProjMember addProjectMember(UpdateProjectMemberReq updateProjectMemberReq);

    Boolean removeProjMember(UpdateProjectMemberReq updateProjectMemberReq);
}