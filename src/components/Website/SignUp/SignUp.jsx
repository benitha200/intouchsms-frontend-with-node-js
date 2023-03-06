import React, { useState } from 'react'
// import './Login.css'
import './SignUp.css'
import axios from 'axios'
// import heroImg from '../../../assets/images/3.jpg'
// import { Form } from 'react-router-dom'
import { Form, Button, FormGroup, Input } from 'reactstrap'
import { useNavigate } from 'react-router-dom'
import { CheckBox } from '@mui/icons-material'
import { InputGroup } from 'react-bootstrap'
import { BsEnvelope, BsFillKeyFill, BsKeyFill, BsPerson, BsPersonCheckFill, BsPersonFill, BsSuitHeart, BsTelephoneFill } from 'react-icons/bs'
const SignUp = () => {

    return (
        <div>

            <div className='hero-content d-flex flex-row'>

                <div className='content-left'>

                    <p className='content-header'>SMS Gateway</p>
                    <span>Your Customer Engagement Platform</span>

                    <button className='hero-button animated bounce'>Sign Up and Start Messaging</button>

                    <span>
                        With simple API intergration and cost-effective solutions,<br /> we connect you to your customers
                    </span>
                </div>
                <div className='content-right mt-0'>
                    <div className='login-form mt-0 p-3'>
                        <span className='form-title'>Create Account </span>
                        <hr color='white' />
                        {/* <Form onSubmit={onSubmit}> */}
                        <Form>
                            <div className='d-flex flex-width gap-2'>
                                <FormGroup>
                                    <InputGroup>
                                        <InputGroup.Text>
                                            <BsPerson />
                                        </InputGroup.Text>


                                        <Input
                                            type='text'
                                            className='form-control'
                                            placeholder='Username'
                                        // name='username' 
                                        // onChange={handleChange}
                                        //   onChange={(e) => setUsername(e.target.value)}
                                        //   value={username}
                                        />
                                    </InputGroup>

                                </FormGroup>

                                <FormGroup>
                                    <InputGroup>
                                        <InputGroup.Text>
                                            <BsPersonFill />
                                        </InputGroup.Text>
                                        <Input
                                            type='text'
                                            className='form-control'
                                            placeholder='Full Name'
                                        // name='username' 
                                        // onChange={handleChange}
                                        //   onChange={(e) => setUsername(e.target.value)}
                                        //   value={username}
                                        />

                                    </InputGroup>

                                </FormGroup>
                            </div>
                            <div className='d-flex flex-width gap-2'>
                                <FormGroup>
                                    <InputGroup>
                                        <InputGroup.Text>
                                            <BsTelephoneFill />
                                        </InputGroup.Text>
                                        <Input
                                            type='text'
                                            className='form-control'
                                            placeholder='Mobile Phone'
                                        // name='username' 
                                        // onChange={handleChange}
                                        //   onChange={(e) => setUsername(e.target.value)}
                                        //   value={username}
                                        />
                                    </InputGroup>


                                </FormGroup>
                                <FormGroup>
                                    <InputGroup>
                                        <InputGroup.Text>
                                            <BsEnvelope />
                                        </InputGroup.Text>
                                        <Input
                                            type='text'
                                            className='form-control'
                                            placeholder='Email Address'
                                        // name='username' 
                                        // onChange={handleChange}
                                        //   onChange={(e) => setUsername(e.target.value)}
                                        //   value={username}
                                        />

                                    </InputGroup>

                                </FormGroup>
                            </div>
                            <div className='d-flex flex-width gap-2'>
                                <FormGroup>
                                    <InputGroup>
                                    <InputGroup.Text>
                                    <BsKeyFill/>
                                    </InputGroup.Text>
                                        <Input
                                            type='text'
                                            className='form-control'
                                            placeholder='Password'
                                        // name='username' 
                                        // onChange={handleChange}
                                        //   onChange={(e) => setUsername(e.target.value)}
                                        //   value={username}
                                        />
                                    </InputGroup>


                                </FormGroup>
                                <FormGroup>
                                    <InputGroup>
                                    <InputGroup.Text>
                                    <BsFillKeyFill/>
                                    </InputGroup.Text>
                                     <Input
                                        type='text'
                                        className='form-control'
                                        placeholder='Repeat Password'
                                    // name='username' 
                                    // onChange={handleChange}
                                    //   onChange={(e) => setUsername(e.target.value)}
                                    //   value={username}
                                    />
                                    </InputGroup>
                                   

                                </FormGroup>
                            </div>

                            <FormGroup>
                                {/* <CheckBox className='text-light'>I have Read ad Accepted the terms of use</CheckBox> */}
                                <InputGroup.Text className='bg-dark text-light border-0 opacity-75'>
                                    <Input type='checkbox' aria-label='' />I have Read ad Accepted the terms of use
                                </InputGroup.Text>


                            </FormGroup>

                            <FormGroup>
                                <Button type="submit" className='app-buttons text-dark'>
                                    Create Account
                                </Button>
                            </FormGroup>
                            <FormGroup>
                                <span className='text-light'> Already have an account ? <a href='/login'>Login</a> </span>
                            </FormGroup>
                        </Form>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default SignUp