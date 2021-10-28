package com.jwd.service.serviceLogic;

import com.jwd.service.domain.Client;
import com.jwd.service.domain.ClientDto;
import com.jwd.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    /**
     *
     * @param client - to be saved in app, received from UI
     * @return ClientDto to display saved user
     */
    ClientDto registerUser(final Client client) throws ServiceException;

    ClientDto login(Client client) throws ServiceException;

    /**
     * returns all users in our app
     * @return List<ClientDto>
     */
    List<ClientDto> getClients() throws ServiceException;
}
