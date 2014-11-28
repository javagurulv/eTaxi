package ua.etaxi.core.handlers.permissions;

import static junit.framework.TestCase.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ua.etaxi.core.commands.permissions.CreatePermissionCommand;
import ua.etaxi.core.commands.permissions.CreatePermissionCommandResult;
import ua.etaxi.core.database.EntityRepository;
import ua.etaxi.core.domain.Permission;

@RunWith(MockitoJUnitRunner.class)
public class CreatePermissionCommandHandlerTest {
    
    @Mock
    private EntityRepository entityRepository;
    
    @InjectMocks
    private CreatePermissionCommandHandler handler = new CreatePermissionCommandHandler();
    
    
    @Test
    public void shouldThrowExceptionWhenCommandIsNull() {
        try {
            handler.execute(null);
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage(), is("CreatePermissionCommand must not be null"));
        }
    }

    @Test
    public void shouldThrowExceptionWhenPermissionNameIsNull() {
        try {
            handler.execute(new CreatePermissionCommand(null));
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Permission Name must not be null"));
        }
    }

    @Test
    public void shouldStorePermissionToDatabase() {
        CreatePermissionCommand command = new CreatePermissionCommand("admin");
        handler.execute(command);
        Mockito.verify(entityRepository, times(1)).create(argThat(new PermissionMatcher("admin")));
    }

    @Test
    public void shouldReturnPermission() {
        CreatePermissionCommand command = new CreatePermissionCommand("admin");
        CreatePermissionCommandResult result = handler.execute(command);
        assertThat(result, is(notNullValue()));
        assertThat(result.getPermission(), is(notNullValue()));
        assertThat(result.getPermission().getPermissionName(), is("admin"));
    }
    
    
    class PermissionMatcher extends ArgumentMatcher<Permission> {

        private String permissionName;

        PermissionMatcher(String permissionName) {
            this.permissionName = permissionName;
        }

        @Override
        public boolean matches(Object o) {
            Permission permission = (Permission)o;
            return permissionName.equals(permission.getPermissionName());
        }
    }
    
}