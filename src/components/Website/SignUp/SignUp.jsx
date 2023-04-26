import { useState } from 'react'
// import './Login.css'
import './SignUp.css'
// import axios from 'axios'
// // import heroImg from '../../../assets/images/3.jpg'
// // import { Form } from 'react-router-dom'
// import { Form, Button, FormGroup, Input } from 'reactstrap'
// import { useNavigate } from 'react-router-dom'
// import { CheckBox } from '@mui/icons-material'
// import { InputGroup } from 'react-bootstrap'
// import { BsEnvelope, BsFillKeyFill, BsKeyFill, BsPerson, BsPersonCheckFill, BsPersonFill, BsSuitHeart, BsTelephoneFill } from 'react-icons/bs'


const SignUp = () => {


    const [username, setUsername] = useState()
    const [names, setNames] = useState()
    const [phone, setPhone] = useState()
    const [email, setEmail] = useState()
    const [password, setPassword] = useState()



    return (
        <div>

            <div className='signup-page p-0 pb-5 pt-2'>


                <div className='left-side pt-0 gap-4 pl-0'>

                    {/* <img src={bulkMessage} width="100" height="100"/> */}

                    <p className='content-header h2 font-weight-bold '>SMS Gateway</p>
                    <span className='signup-text'>Your Customer Engagement Platform</span>

                    <button className='hero-button btn animated bounce'>Sign Up and Start Messaging</button>

                    <span className='signup-text'>
                        With simple API intergration and cost-effective solutions,<br /> we connect you to your customers
                    </span>
                </div>

                <div className='form-holder shadow p-3 mt-5 mb-5 bg-light h-100 gap-2 pb-5'>
                    <span className='form-title font-weight-bold h4'>Create Account </span>
                    <hr color='white' />

                    <form className='d-flex flex-column gap-3'
                        encType='multipart/form-data'
                    // onSubmit={login}
                    >
                        <div>
                            <input
                                type="text"
                                placeholder='Username'
                                className='form-control border-bottom rounded-0'
                                onChange={(e) => setUsername(e.target.value)}
                                value={username}
                            />
                        </div>
                        <div>
                            <input
                                type="text"
                                placeholder='Full Name'
                                className='form-control border-bottom rounded-0'
                                onChange={(e) => setNames(e.target.value)}
                                value={names}
                            /></div>
                        <div>
                            <input
                                type="text"
                                placeholder='Mobile Phone'
                                className='form-control border-bottom rounded-0'
                                onChange={(e) => setPhone(e.target.value)}
                                value={phone}
                            />
                        </div>
                        <div>
                            <input
                                type="text"
                                placeholder='E-mail'
                                className='form-control border-bottom rounded-0'
                                onChange={(e) => setEmail(e.target.value)}
                                value={email}
                            /></div>
                        <div>
                            <input
                                type="password"
                                placeholder='password'
                                className='form-control border-bottom rounded-0 width-25'
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <div>
                            <input
                                type="password"
                                placeholder='Confirm Password'
                                className='form-control border-bottom rounded-0 width-25'
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <button type="submit" className='app-buttons w-75 ml-5 text-dark'> Sign Up </button>


                    </form>

                    <hr color="grey" />
                    <span className='text-dark'> Already have an account ? <a href='/login'> Login</a></span>

                </div>


            </div>
        </div>
    )
}

export default SignUp