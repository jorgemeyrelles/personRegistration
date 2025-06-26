package br.com.personreg.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.personreg.application.dto.mapper.UserMapper;
import br.com.personreg.application.dto.request.UserRequestDTO;
import br.com.personreg.application.dto.request.UserUpdateRequestDTO;
import br.com.personreg.application.dto.response.UserResponseDTO;
import br.com.personreg.domain.entity.Role;
import br.com.personreg.domain.entity.RoleName;
import br.com.personreg.domain.entity.User;
import br.com.personreg.domain.exception.DuplicateResourceException;
import br.com.personreg.domain.exception.ResourceNotFoundException;
import br.com.personreg.domain.repository.PersonRepository;
import br.com.personreg.domain.repository.RoleRepository;
import br.com.personreg.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Create a new user
     * @param dto the user data
     * @return the created user
     */
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto) {
        // Check if username already exists
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("User", "username", dto.getUsername());
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("User", "email", dto.getEmail());
        }
        
        // Check if CPF already exists
        if (personRepository.existsByCpf(dto.getPerson().getCpf())) {
            throw new DuplicateResourceException("Person", "CPF", dto.getPerson().getCpf());
        }
        
        // Get role
        Role role = roleRepository.findByName(RoleName.valueOf(dto.getRole()))
            .orElseThrow(() -> new ResourceNotFoundException("Role", "name", dto.getRole()));
        
        // Map DTO to entity
        User user = userMapper.toEntity(dto);
        
        // Set role
        user.setRole(role);
        
        // Encode password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // Save user
        User savedUser = userRepository.save(user);
        
        // Return response DTO
        return userMapper.toDto(savedUser);
    }
    
    /**
     * Get all users (only accessible by COORDINATOR)
     * @param currentUser the currently authenticated user
     * @return list of all users
     */
    @Transactional(dontRollbackOn = {IllegalArgumentException.class})
    public List<UserResponseDTO> getAllUsers(User currentUser) {
        // Check if current user is a COORDINATOR
        if (!currentUser.getRole().getName().equals(RoleName.COORDINATOR)) {
            throw new AccessDeniedException("Only coordinators can access all users");
        }
        
        return userRepository.findAll().stream()
            .map(userMapper::toDto)
            .collect(Collectors.toList());
    }
    
    /**
     * Get a user by ID
     * @param id the user ID
     * @param currentUser the currently authenticated user
     * @return the user with the given ID
     */
    @Transactional(dontRollbackOn = {IllegalArgumentException.class})
    public UserResponseDTO getUserById(UUID id, User currentUser) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Check if current user is the requested user or a COORDINATOR/OPERATOR
        if (!currentUser.getId().equals(user.getId()) && 
            !currentUser.getRole().getName().equals(RoleName.COORDINATOR) &&
            !currentUser.getRole().getName().equals(RoleName.OPERATOR)) {
            throw new AccessDeniedException("Access denied");
        }
        
        return userMapper.toDto(user);
    }
    
    /**
     * Update a user
     * @param id the ID of the user to update
     * @param dto the updated user data
     * @param currentUser the currently authenticated user
     * @return the updated user
     */
    @Transactional
    public UserResponseDTO updateUser(UUID id, UserUpdateRequestDTO dto, User currentUser) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Check if current user is the requested user or a COORDINATOR
        if (!currentUser.getId().equals(user.getId()) && 
            !currentUser.getRole().getName().equals(RoleName.COORDINATOR)) {
            throw new AccessDeniedException("You can only update your own profile unless you are a coordinator");
        }
        
        // Check username uniqueness if being updated
        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername()) &&
            userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("User", "username", dto.getUsername());
        }
        
        // Check email uniqueness if being updated
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail()) &&
            userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("User", "email", dto.getEmail());
        }
        
        // Update user with data from DTO
        userMapper.updateEntityFromDto(dto, user);
        
        // Encode password if provided
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        // Save updated user
        User updatedUser = userRepository.save(user);
        
        return userMapper.toDto(updatedUser);
    }
    
    /**
     * Delete a user
     * @param id the ID of the user to delete
     * @param currentUser the currently authenticated user
     */
    @Transactional
    public void deleteUser(UUID id, User currentUser) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // Check if current user is a COORDINATOR or OPERATOR
        if (!currentUser.getRole().getName().equals(RoleName.COORDINATOR) &&
            !currentUser.getRole().getName().equals(RoleName.OPERATOR)) {
            throw new AccessDeniedException("Only coordinators and operators can delete users");
        }
        
        // Regular users cannot be deleted by operators
        if (currentUser.getRole().getName().equals(RoleName.OPERATOR) &&
            user.getRole().getName().equals(RoleName.COORDINATOR)) {
            throw new AccessDeniedException("Operators cannot delete coordinators");
        }
        
        userRepository.delete(user);
    }
}
