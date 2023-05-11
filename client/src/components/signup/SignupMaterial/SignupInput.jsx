import styled from 'styled-components';
import { useSelector, useDispatch } from 'react-redux';
import { setFan, setArtist } from '../../../reducer/signupSlice';
import { emailValidation, pwdValidation, checkPwdValidation } from '../validation.js';
import { BsCheckLg } from 'react-icons/bs';
import axios from 'axios';
import { useRef } from 'react';

const InputForm = styled.form`
  hr {
    border: none;
    margin: 10px 0;
  }
  label {
    height: 16px;
    font-size: 13px;
    line-height: 18.2px;
    color: rgb(109, 109, 109);
  }
  .input-box {
    height: 42px;
    margin: 2px 0 1px 0;
    display: flex;
    position: relative;
    > input {
      flex-grow: 1;
      font-size: 15px;
      line-height: 21px;
      color: rgb(89, 95, 99);
      border-bottom: 0.1rem solid rgb(238, 238, 238);
    }
    input:focus ~ .hrtag::after {
      width: 100%;
      z-index: 1;
    }
  }
`;
const HrTag = styled.hr`
  &::after {
    content: '';
    position: absolute;
    left: 0px;
    bottom: -0.1rem;
    width: 0px;
    height: 2px;
    background: linear-gradient(90deg, #95c788, #1cbec8);
    transition: all 0.3s linear 0s;
  }
`;

const EmailCheck = styled.div`
  font-size: 11px;
  width: 50px;
  height: 25px;
  font-weight: 500;
  color: var(--gray-850);
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
  cursor: pointer;
  border: 1px solid var(--light-gray-400);
  border-radius: 0.5rem;
  padding: 2px 5px;
  :hover {
    font-weight: 600;
  }
`;

const CheckBox = styled.div`
  border: 1px solid #95c788;
  border-radius: 6px;
  width: 25px;
  height: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

const CheckIcon = styled(BsCheckLg)`
  color: #1cbec8;
  font-size: 21px;
  margin-right: 1px;
`;

const ValidDesc = styled.p`
  margin: 10px 0 20px;
  font-size: 11px;
  color: rgb(109, 109, 109);
  &.false-input {
    color: rgb(253, 91, 21);
  }
`;
const SignupInput = ({ emailRef, pwdRef, pwdCheckRef, nameRef, nicknnameRef }) => {
  // signup을 위한 reducer 전역상태 받아오기
  const isArtist = useSelector((state) => state.signup.calssification);
  const fanUser = useSelector((state) => state.signup.fan);
  const artist = useSelector((state) => state.signup.artist);
  const dispatch = useDispatch();

  //input제어

  const onChange = (e) => {
    const { value, name } = e.target;

    if (isArtist) {
      dispatch(setArtist({ ...artist, [name]: value }));
    } else {
      dispatch(setFan({ ...fanUser, [name]: value }));
    }
  };

  //중복확인
  const onClickCheckBox = async () => {
    let curEmail = '';
    if (isArtist) {
      curEmail = artist.email;
    } else {
      curEmail = fanUser.email;
    }
    alert('사용가능');
    // 중복검사 백엔드분께 물어보기
    // await axios
    //   .post('http://localhost:4000/fans', {
    //     email: curEmail,
    //   })
    //   .then();
  };

  return (
    <>
      <InputForm>
        <label htmlFor='email'>이메일</label>
        <div className='input-box'>
          <input ref={emailRef} type='email' id='email' name='email' onChange={onChange} placeholder='your@email.com'></input>
          <EmailCheck onClick={onClickCheckBox}>
            중복확인
            {/* <CheckBox onClick={onClickCheckBox}>
              <CheckIcon />
            </CheckBox> */}
          </EmailCheck>
          <HrTag className='hrtag' />
        </div>
        {isArtist ? (
          emailValidation(artist.email)[0] ? (
            <hr />
          ) : (
            <ValidDesc className='false-input'>{emailValidation(artist.email)[1]}</ValidDesc>
          )
        ) : emailValidation(fanUser.email)[0] ? (
          <hr />
        ) : (
          <ValidDesc className='false-input'>{emailValidation(fanUser.email)[1]}</ValidDesc>
        )}

        <label htmlFor='password'>비밀번호</label>
        <div className='input-box'>
          <input ref={pwdRef} type='password' id='password' name='password' onChange={onChange} placeholder='영문+숫자+특수문자 최소 8자리'></input>
          <HrTag className='hrtag' />
        </div>
        {isArtist ? (
          pwdValidation(artist.password)[0] ? (
            <hr />
          ) : (
            <ValidDesc className='false-input'>{pwdValidation(artist.password)[1]}</ValidDesc>
          )
        ) : pwdValidation(fanUser.password)[0] ? (
          <hr />
        ) : (
          <ValidDesc className='false-input'>{pwdValidation(fanUser.password)[1]}</ValidDesc>
        )}

        <label htmlFor='password-check'>비밀번호 확인</label>
        <div className='input-box'>
          <input ref={pwdCheckRef} type='password' id='password-check' name='passwordCheck' onChange={onChange} placeholder='비밀번호 확인'></input>
          <HrTag className='hrtag' />
        </div>
        {isArtist ? (
          checkPwdValidation(artist.password, artist.passwordCheck)[0] ? (
            <hr />
          ) : (
            <ValidDesc className='false-input'>{checkPwdValidation(artist.password, artist.passwordCheck)[1]}</ValidDesc>
          )
        ) : checkPwdValidation(fanUser.password, fanUser.passwordCheck)[0] ? (
          <hr />
        ) : (
          <ValidDesc className='false-input'>{checkPwdValidation(fanUser.password, fanUser.passwordCheck)[1]}</ValidDesc>
        )}
        <label htmlFor='name'>이름</label>
        <div className='input-box'>
          <input ref={nameRef} type='text' id='password-check' name='name' onChange={onChange} placeholder='이름'></input>
          <HrTag className='hrtag' />
        </div>
        <hr />
        <label htmlFor='nickname'>닉네임</label>
        <div className='input-box'>
          <input ref={nicknnameRef} type='text' id='nickname' name='nickname' onChange={onChange} placeholder='닉네임'></input>
          <HrTag className='hrtag' />
        </div>
        <hr></hr>
      </InputForm>
    </>
  );
};

export default SignupInput;
