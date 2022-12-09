package com.unitechApi.user.controller;

import com.unitechApi.Payload.request.LoginRequest;
import com.unitechApi.Payload.request.SignupRequest;
import com.unitechApi.Payload.response.JwtResponse;
import com.unitechApi.Payload.response.MessageResponse;
import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.common.query.SearchRequest;
import com.unitechApi.common.query.SearchSpecification;
import com.unitechApi.exception.ExceptionService.PasswordIncorrect;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.RoleNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.jwt.JwtUtils;
import com.unitechApi.user.Repository.*;
import com.unitechApi.user.model.*;
import com.unitechApi.user.service.Notification;
import com.unitechApi.websecurity.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

//security.UserDetailsImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/unitech/api/v1/user")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final SessionRegistry sessionRegistry;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PassWordRepository pa;
    private final ExperienceRepository experienceRepository;
    private final QualificationRepository qualificationRepository;
    private final HrRepository hrRepository;
    private final FamilyDetailsRepository familyDetailsRepository;
    private final UserNotificationService userNotificationService;

    public AuthController(SessionRegistry sessionRegistry, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, JwtUtils jwtUtils, PassWordRepository pa, ExperienceRepository experienceRepository, QualificationRepository qualificationRepository, HrRepository hrRepository, FamilyDetailsRepository familyDetailsRepository, UserNotificationService userNotificationService) {
        this.sessionRegistry = sessionRegistry;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.pa = pa;
        this.experienceRepository = experienceRepository;
        this.qualificationRepository = qualificationRepository;
        this.hrRepository = hrRepository;
        this.familyDetailsRepository = familyDetailsRepository;
        this.userNotificationService = userNotificationService;
    }

    @GetMapping("/time")
    public ZonedDateTime dateTime() {
        ZonedDateTime dateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Paris")); // Gets the current date and time, with your default time-zone
        ZonedDateTime asiaDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Kolkata")); // Gets the current date and time, with your default time-zone

        logger.info("zoned date Europe time With Zone {}",dateTime);
        logger.info("zoned date Asia time With Zone {}",asiaDate);
        return asiaDate;
                //LocalDateTime.from(Instant.now().atZone(ZoneId.of("Asia/Kolkata")));
        //LocalDateTime.now().atZone(ZoneId.of("Asia/Kolkata"))
    }

    @PostMapping("/notification/{name}")
    public ResponseEntity<?> send(@PathVariable String name, @RequestBody Notification notification) {
        userNotificationService.pushNotification(name, notification.getUser());
        return ResponseEntity.ok().body("successfully done" + name);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//   @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN') or hasRole('ROLE_MAINTENANCE') or hasRole('ROLE_STORE') or hasRole('ROLE_GENERALMANAGER')")
    public Optional<User> FindById(@PathVariable Long id) {

        return userRepository.findById(id);
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getPhoneno(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getPhoneno(),
                    userDetails.getAddress(),
                    userDetails.getStatus(),
                    userDetails.getDatetime(), roles));
        } catch (PasswordIncorrect e) {
            throw new  PasswordIncorrect("password incorrect ");
        }
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }


        User user = new User(signUpRequest.getUsername()
                , signUpRequest.getEmail()
                , signUpRequest.getAddress()
                , signUpRequest.getPhoneno()
                , signUpRequest.getTelephoneNumber()
                , signUpRequest.getDob()
                , signUpRequest.getMaritalStatus()
                , signUpRequest.getNativePalace()
                , signUpRequest.getNationality()
                , signUpRequest.getPinCode()
                , signUpRequest.getBloodGroup()
                , signUpRequest.getIndentification()
                );

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        System.out.println(signUpRequest.getRoles());
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CASTER)
                    .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
            System.out.println(userRole.getName());
            roles.add(userRole);
            System.out.println("null values");
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(adminRole);

                        System.out.println("admin values");
                        break;
                    case "SUBADMIN":
                        Role modRole = roleRepository.findByName(ERole.ROLE_SUBADMIN)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(modRole);
                        System.out.println("mod values");
                        break;
                    case "GM":
                        Role gm = roleRepository.findByName(ERole.ROLE_GENERALMANAGER)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(gm);
                        System.out.println("mod values");
                        break;
                    case "PURCHASER":
                        Role purchaser = roleRepository.findByName(ERole.ROLE_PURCHASER)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(purchaser);
                        System.out.println("mod values");
                        break;
                    case "ACCOUNT":
                        Role account = roleRepository.findByName(ERole.ROLE_ACCOUNT)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(account);
                        System.out.println("mod values");
                        break;
                    case "SUPERVISOR":
                        Role supervisor = roleRepository.findByName(ERole.ROLE_SUPERVISOR)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(supervisor);
                        System.out.println("mod values");
                        break;
                    case "QC":
                        Role qc = roleRepository.findByName(ERole.ROLE_QC)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(qc);
                        System.out.println("mod values");
                        break;
                    case "HR":
                        Role hr = roleRepository.findByName(ERole.ROLE_HR)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(hr);
                        System.out.println("mod values");
                        break;
                    case "SECURITY":
                        Role security = roleRepository.findByName(ERole.ROLE_SECURITY)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(security);
                    case "LEBOR":
                        Role lebor = roleRepository.findByName(ERole.ROLE_LEBOR)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(lebor);
                    case "MAINTENANCE":
                        Role maintenance = roleRepository.findByName(ERole.ROLE_MAINTENANCE)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(maintenance);
                    case "STORE":
                        Role store = roleRepository.findByName(ERole.ROLE_STORE)
                                .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));
                        roles.add(store);
                        System.out.println("default values");
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok((user));
    }



    /*
     * password  save for admin and subadmin and manager and hr
     * */


    @RequestMapping(value = "/user/findAll", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public ResponseEntity<Map<String, Object>> FindAll(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        try {
            List<User> user;
            org.springframework.data.domain.Pageable paging = PageRequest.of(page, size, Sort.by("id"));

            Page<User> pageTuts = userRepository.findAll(paging);
            user = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("Total_User", user);
            response.put("current Page", pageTuts.getNumber());
            response.put("total Items", pageTuts.getTotalElements());
            response.put("total Pages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public void UserDeleteById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }


    @PatchMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN') or hasRole('ROLE_MAINTENANCE') or hasRole('ROLE_STORE') or hasRole('ROLE_GENERALMANAGER')")
    public ResponseEntity<User> updateTutorial(@PathVariable("id") long id, @RequestBody Map<Object, Object> fields) {
        Optional<User> ma = userRepository.findById(id);
        if (ma.isPresent()) {
            fields.forEach((key, value) -> {
                        Field field = ReflectionUtils.findField(User.class, (String) key);
                assert field != null;
                field.setAccessible(true);
                        ReflectionUtils.setField(field, ma.get(), value);
                    }
            );
            userRepository.save(ma.get());

        } else {
            throw new UserNotFound("User Not Found this id" + id);
        }
        return null;
    }


    @PostMapping("/user/findbyuser")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public ResponseEntity<User> findByUser(@RequestParam String username) {
        return ResponseEntity.ok(userRepository.findByUser(username));
    }

    @GetMapping("/")
    public ResponseEntity<?> FindByIdAndStatus(@RequestParam Long id, @RequestParam ConfirmatioStatus status) {
        return ResponseEntity.ok(userRepository.findByIdAndConfirmatioStatus(id, ConfirmatioStatus.valueOf(String.valueOf(status))));
    }

    @GetMapping("/statusA/{id}")
    public ResponseEntity<?> StatusChange(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("resource not found"));

        user.setConfirmatioStatus(ConfirmatioStatus.ACCEPT);
        userRepository.save(user);

        return new ResponseEntity<>(new MessageResponse("update"), HttpStatus.OK);
    }

    @GetMapping("/statusR/{id}")
    public ResponseEntity<?> rejectStatusChange(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("resource not found"));
        user.setConfirmatioStatus(ConfirmatioStatus.REJECT);
        userRepository.save(user);

        return new ResponseEntity<>(new MessageResponse("update"), HttpStatus.OK);
    }

    @PutMapping("/{Hrid}/hrfilldataTouser/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    private HrModel FillDataToUser(@PathVariable Long Hrid, @PathVariable Long uid) {
        User userProfileModel = userRepository.findById(uid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        HrModel hrModel = hrRepository.findById(Hrid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        hrModel.setUserProfileModel(userProfileModel);
        return hrRepository.save(hrModel);
    }

    /*
      id Update password to user
      * */
    @PutMapping("/{pass_id}/IdUpdatePass/{user_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public PasswordEntity IdUpdate(@PathVariable Long pass_id, @PathVariable Long user_id) {
        PasswordEntity passwordEntity = pa.findById(pass_id).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        User user = userRepository.findById(user_id).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        passwordEntity.idUpdate(user);
        return pa.save(passwordEntity);
    }

    @PutMapping("/{eid}/expTouser/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public ExperienceModel AddDetails(@PathVariable Long eid, @PathVariable Long uid) {
        User userProfileModel = userRepository.findById(uid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        ExperienceModel experienceModel = experienceRepository.findById(eid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        experienceModel.AddExperience(userProfileModel);
        return experienceRepository.save(experienceModel);
    }

    @PutMapping("/{quaid}/quaTouser/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public QualificationModel AddQualificationToUserProfile(@PathVariable Long quaid, @PathVariable Long uid) {
        User userProfileModel = userRepository.findById(uid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        QualificationModel qualificationModel = qualificationRepository.findById(quaid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        qualificationModel.AddQualification(userProfileModel);
        return qualificationRepository.save(qualificationModel);
    }

    @PutMapping("/{fid}/familyDetailsToUser/{uid}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public FamilyDetailsModel updatefamilyTouser(@PathVariable Long fid, @PathVariable Long uid) {
        User userProfileModel = userRepository.findById(uid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        FamilyDetailsModel familyDetailsModel = familyDetailsRepository.findById(fid).orElseThrow(()->new ResourceNotFound("Resource Not Found"));
        familyDetailsModel.AddFamilyDetails(userProfileModel);
        return familyDetailsRepository.save(familyDetailsModel);
    }
    @GetMapping(value = "/value")
    public ResponseEntity<?> findAllLoginData()
    {
        List<Object> data=sessionRegistry.getAllPrincipals();
        List<String> usersNamesList = new ArrayList<>();

        for (Object principal: data) {
            if (principal instanceof User) {
                usersNamesList.add(((User) principal).getUsername());
            }
        }

        return new ResponseEntity<>(PageResponse.SuccessResponse(usersNamesList),HttpStatus.ACCEPTED);
    }
    @GetMapping(value = "/getSearchingUser")
    public ResponseEntity<?> getSearchingUser(@RequestBody SearchRequest searchRequest)
    {
        SearchSpecification<User> data=new SearchSpecification<>(searchRequest);
        Pageable pageable=SearchSpecification.getPageable(searchRequest.getPage(),searchRequest.getSize());
        Page<User> check=userRepository.findAll(data,pageable);
        return new ResponseEntity<>(userRepository.findAll((Pageable)check),HttpStatus.OK);
    }

}
